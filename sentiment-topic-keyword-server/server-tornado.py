import tornado.ioloop
import tornado.web
import tornado.httpserver

from TopicsHandler import TopicsHandler
from SentimentHandler import SentimentHandler

SERVER_PORT = 9090


class MainHandler(tornado.web.RequestHandler):
    def get(self):
        self.write("Server Running ... ")


class Application(tornado.web.Application):
    def __init__(self):
        handlers = [
            (r"/api/sentiment", SentimentHandler),
            (r"/api/topic", TopicsHandler),
            (r"/", MainHandler),
        ]
        settings = dict(
            app_title=u"Sentiment and Category API",
            debug=True,
        )
        tornado.web.Application.__init__(self, handlers, **settings)


def main():
    print "Building Sentiment and Topic models. Please Wait for Server to start."
    http_server = tornado.httpserver.HTTPServer(Application())
    http_server.listen(SERVER_PORT)
    print "Server started at Port ", SERVER_PORT
    tornado.ioloop.IOLoop.instance().start()


if __name__ == "__main__":
    main()
