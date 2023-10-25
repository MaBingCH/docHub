package main

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

func main() {
	engine := gin.New()
	engine.UseH2C = true
	engine.GET("/", func(c *gin.Context) {
		c.JSON(http.StatusOK, "gin for h2")
	})
	engine.RunTLS(":9180", "localhost.crt", "localhost.key")
}
