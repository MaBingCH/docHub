# 生成公钥私钥文件
openssl req -newkey rsa:2048 -nodes -keyout server.key -x509  -days 365 -out server.crt

server.key：服务端私钥
server.crt：证书文件

参照命令
1，生成服务端私钥
openssl genrsa -out default.key 2048
2，生成服务端证书
openssl req -new -x509 -key default.key -out default.pem -days 3650

## x509: certificate relies on legacy Common Name field, use SANs instead 
openssl genrsa -out ca.key 2048
openssl req -new -x509 -days 365 -key ca.key -subj "/C=CN/ST=HB/L=WH/O=Test./CN=Root CA" -out ca.crt
openssl req -newkey rsa:2048 -nodes -keyout localhost.key -subj "/C=CN/ST=GD/L=SZ/O=Test./CN=*" -out localhost.csr
openssl x509 -req -extfile <(printf "subjectAltName=DNS:127.0.0.1,DNS:localhost") -days 365 -in localhost.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out localhost.crt

# http2 with net/http

srv := &http.Server{Addr: ":9900", Handler: http.HandlerFunc(handle)}
srv.ListenAndServeTLS("server.crt", "server.key")
## h2c
```
	h2s := &http2.Server{}
	server := &http.Server{
		Addr:    "0.0.0.0:1010",
		Handler: h2c.NewHandler(handler, h2s),
	}
	server.ListenAndServe()
```
# http2 with gin
```
engine := gin.New()
engine.RunTLS(":9180", "localhost.crt", "localhost.key")
```
## h2c
```
engine := gin.New()
engine.UseH2C = true
engine.Run(":9180")
```


