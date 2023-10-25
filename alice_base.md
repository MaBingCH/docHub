```
import (
	"fmt"
	"net/http"
	"time"

	"github.com/containous/alice"
)

func timeoutHandler(h http.Handler) http.Handler {
	fmt.Println("==================timeoutHandler=========")
	return http.TimeoutHandler(h, 1*time.Second, "timed out")
}

func myApp(w http.ResponseWriter, r *http.Request) {
	fmt.Println("==================myApp=========")
	w.Write([]byte("Hello world!"))
}

func timeoutHandler2(h http.Handler) http.Handler {
	fmt.Println("==================timeoutHandler22=========")
	return http.TimeoutHandler(h, 1*time.Second, "timed2 out")
}

// 中间件函数：记录请求信息
func loggingMiddleware(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		fmt.Println("Logging Middleware: Received request")
		next.ServeHTTP(w, r)
		fmt.Println("Logging Middleware: Request completed")
	})
}

// 中间件函数：进行身份验证
func authMiddleware(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		// 在这里可以进行身份验证逻辑，例如检查令牌或用户名密码
		// 如果身份验证失败，可以在这里发送适当的响应并终止请求

		// 这里简化为只输出信息
		fmt.Println("Authentication Middleware: Authenticating request")
		next.ServeHTTP(w, r)
	})
}

func main() {
	// 创建一个新的Alice中间件链
	chain := alice.New(loggingMiddleware, authMiddleware).ThenFunc(myApp)
	http.ListenAndServe(":8000", chain)
}
```
