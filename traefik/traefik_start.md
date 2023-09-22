# 启动过程
traefik.go
...
func runCmd(staticConfiguration *static.Configuration) error {  
...
```
svr, err := setupServer(staticConfiguration)  
   -> serverEntryPointsTCP, err := server.NewTCPEntryPoints(staticConfiguration.EntryPoints, staticConfiguration.HostResolver, metricsRegistry)  
     -> server_entrypoint_tcp.go    NewTCPEntryPoint  
        -> server_entrypoint_tcp.go   buildListener  
	    listener, err := net.Listen("tcp", entryPoint.GetAddress())  #开始监听端口
	-> server_entrypoint_tcp.go   createHTTPServer
		serverHTTP := &http.Server{
			Handler:      handler,
			ErrorLog:     stdlog.New(logs.NoLevel(log.Logger, zerolog.DebugLevel), "", 0),
			ReadTimeout:  time.Duration(configuration.Transport.RespondingTimeouts.ReadTimeout),
			WriteTimeout: time.Duration(configuration.Transport.RespondingTimeouts.WriteTimeout),
			IdleTimeout:  time.Duration(configuration.Transport.RespondingTimeouts.IdleTimeout),
		}
		...
		listener := newHTTPForwarder(ln)    #将tcp的conn转发给http httpForwarder
		go func() {
			err := serverHTTP.Serve(listener)  #http server 启动
			if err != nil && !errors.Is(err, http.ErrServerClosed) {
				log.Ctx(ctx).Error().Err(err).Msg("Error while starting server")
			}
		}()
```
#设定监视退出singal  
ctx, _ := signal.NotifyContext(context.Background(), syscall.SIGINT, syscall.SIGTERM)  
...  

## 启动  tcp conn -> http conn
svr.Start(ctx)  
```
server.go  func (s *Server) Start(ctx context.Context)
	go func() {
			<-ctx.Done()
			logger := log.Ctx(ctx)
			logger.Info().Msg("I have to go...")
			logger.Info().Msg("Stopping server gracefully")
			s.Stop()
		}()

	s.tcpEntryPoints.Start()
	 ->server_entrypoint_tcp.go  Start()
              go serverEntryPoint.Start(ctx)

		for {
			conn, err := e.listener.Accept()  #接受tcp连接
		...
			writeCloser, err := writeCloser(conn) # tcp conn 转换
		...
			e.switcher.ServeTCP(newTrackedConnection(writeCloser, e.tracker)) 
				->     调用httpForwarder的ServeTCP将连接存用channel
                                        // ServeTCP uses the connection to serve it later in "Accept".
					func (h *httpForwarder) ServeTCP(conn tcp.WriteCloser) {
						h.connChan <- conn
					}
				->   serverHTTP.Serve自动调用Accept方法获取conn
                                        // Accept retrieves a served connection in ServeTCP.
					func (h *httpForwarder) Accept() (net.Conn, error) {
						select {
						case conn := <-h.connChan:
							return conn, nil
						case err := <-h.errChan:
							return nil, err
						}
					}
		...
```
defer svr.Close()    
 ...  
 #等待退出signal  
svr.Wait()   
log.Info().Msg("Shutting down")    
