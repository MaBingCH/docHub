# 配置openssl安全证书
openssl genrsa -out server.key 2048
openssl rsa -in server.key -out server.key
openssl req -new -x509 -key server.key -out ca.crt -days 3650

# server.xml的配置
```
<Connector port="8443" protocol="org.apache.coyote.http11.Http11AprProtocol"
               maxThreads="150" SSLEnabled="true"
               maxParameterCount="1000"
               >
        <UpgradeProtocol className="org.apache.coyote.http2.Http2Protocol" />
        <SSLHostConfig>
            <Certificate certificateKeyFile="conf/server.key" 
            certificateFile="conf/ca.crt" />
        </SSLHostConfig>
    </Connector>
```
# JKS
keytool -genkeypair -alias keystore -keyalg RSA -validity 3650 -keystore keystore.jks
```
<Connector port="8443" protocol="org.apache.coyote.http11.Http11AprProtocol"
               maxThreads="150" SSLEnabled="true"
               maxParameterCount="1000"
               >
        <UpgradeProtocol className="org.apache.coyote.http2.Http2Protocol" />
        <SSLHostConfig>
        <Certificate certificateKeystoreFile="conf/keystore.jks"
                     certificateKeystorePassword="keystone"
                     type="RSA" />
        </SSLHostConfig>
    </Connector>
```