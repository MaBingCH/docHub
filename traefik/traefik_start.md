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
	    listener, err := net.Listen("tcp", entryPoint.GetAddress())  
	-> server_entrypoint_tcp.go   createHTTPServer  
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
