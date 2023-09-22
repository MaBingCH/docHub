# 启动过程
traefik.go
...
func runCmd(staticConfiguration *static.Configuration) error {
...

svr, err := setupServer(staticConfiguration)
...
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
