```
traefik.go
roundTripperManager := service.NewRoundTripperManager(spiffeX509Source)
->  roundtripper.go
	// NewRoundTripperManager creates a new RoundTripperManager.
	func NewRoundTripperManager(spiffeX509Source SpiffeX509Source) *RoundTripperManager {
	return &RoundTripperManager{
		roundTrippers:    make(map[string]http.RoundTripper),
		configs:          make(map[string]*dynamic.ServersTransport),
		spiffeX509Source: spiffeX509Source,
	}
	

	->func (r *RoundTripperManager) createRoundTripper(cfg *dynamic.ServersTransport) (http.RoundTripper, error) {
	
		transport := &http.Transport{
			Proxy:                 http.ProxyFromEnvironment,
			DialContext:           dialer.DialContext,
			MaxIdleConnsPerHost:   cfg.MaxIdleConnsPerHost,
			IdleConnTimeout:       90 * time.Second,
			TLSHandshakeTimeout:   10 * time.Second,
			ExpectContinueTimeout: 1 * time.Second,
			ReadBufferSize:        64 * 1024,
			WriteBufferSize:       64 * 1024,
		}
	
		// Return directly HTTP/1.1 transport when HTTP/2 is disabled
		if cfg.DisableHTTP2 {
			return transport, nil
		}

		return newSmartRoundTripper(transport, cfg.ForwardingTimeouts)
...

managerfactory.go
managerFactory := service.NewManagerFactory(*staticConfiguration, routinesPool, metricsRegistry, roundTripperManager, acmeHTTPHandler)
	-> func NewManagerFactory(staticConfiguration static.Configuration, routinesPool *safe.Pool, metricsRegistry metrics.Registry, roundTripperManager *RoundTripperManager, acmeHTTPHandler http.Handler) *ManagerFactory {
		factory := &ManagerFactory{
			metricsRegistry:     metricsRegistry,
			routinesPool:        routinesPool,
			roundTripperManager: roundTripperManager,
			acmeHTTPHandler:     acmeHTTPHandler,
		}


service.go
func (m *Manager) getLoadBalancerServiceHandler(ctx context.Context, serviceName string, info *runtime.ServiceInfo) (http.Handler, error) {

	
		roundTripper, err := m.roundTripperManager.Get(service.ServersTransport)
		lb := wrr.New(service.Sticky, service.HealthCheck != nil)
		for _, server := range shuffle(service.Servers, m.rand) {
			proxy := buildSingleHostProxy(target, passHostHeader, time.Duration(flushInterval), roundTripper, m.bufferPool)

			proxy.go
			func buildSingleHostProxy(target *url.URL, passHostHeader bool, flushInterval time.Duration, roundTripper http.RoundTripper, bufferPool httputil.BufferPool) http.Handler {
				return &httputil.ReverseProxy{
					Director:      directorBuilder(target, passHostHeader),
					Transport:     roundTripper,
					FlushInterval: flushInterval,
					BufferPool:    bufferPool,
					ErrorHandler:  errorHandler,
				}
			}

			func directorBuilder(target *url.URL, passHostHeader bool) func(req *http.Request) {
				return func(outReq *http.Request) {
					outReq.URL.Scheme = target.Scheme
					outReq.URL.Host = target.Host

					u := outReq.URL
					if outReq.RequestURI != "" {
						parsedURL, err := url.ParseRequestURI(outReq.RequestURI)
						if err == nil {
							u = parsedURL
						}
					}

					outReq.URL.Path = u.Path
					outReq.URL.RawPath = u.RawPath
					// If a plugin/middleware adds semicolons in query params, they should be urlEncoded.
					outReq.URL.RawQuery = strings.ReplaceAll(u.RawQuery, ";", "&")
					outReq.RequestURI = "" // Outgoing request should not have RequestURI

					outReq.Proto = "HTTP/1.1"
					outReq.ProtoMajor = 1
					outReq.ProtoMinor = 1

					// Do not pass client Host header unless optsetter PassHostHeader is set.
					if !passHostHeader {
						outReq.Host = outReq.URL.Host
					}

					cleanWebSocketHeaders(outReq)
				}
			}

		lb.Add(proxyName, proxy, nil)
```
