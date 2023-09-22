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
		listener := newHTTPForwarder(ln)    #将tcp的conn转发给http
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

## 启动
svr.Start(ctx)  
defer svr.Close()  
 ...
 #等待退出signal
 	svr.Wait()   
	log.Info().Msg("Shutting down")  
