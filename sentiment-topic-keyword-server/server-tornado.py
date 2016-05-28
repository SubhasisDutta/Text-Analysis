import tornado.ioloop
import tornado.web
import tornado.httpserver
import sys,traceback

from Sentiment.SentiHandlers.SentiMaster import SentiHandle
from Topics import Categorize
import json

SERVER_PORT = 8888

class MainHandler(tornado.web.RequestHandler):
    def get(self):
        self.write("Server Running ... ")

class PyInterfaceServer:
    def __init__(self):
        self.log = {}
        self.S = SentiHandle()
        self.catz = Categorize.Categorize()

    def ping(self):
        print ("Ping Success !! :D")
        return

    def getSentimentScore(self, text):
        '''
        Arguments List:
        general -> mainText,textType = "general"
        microblogs -> mainText, textType = "microblogs"
        comments -> mainText, textType = "comments"
        reviews -> mainText, textType = "reviews", title = "" <optional>,topDomain,subDomain = "" <depends, not always optional, refer the list in config.py>
        blogs_news -> mainText< or first paragraph>, title, textType="blogs_news",lastPara = "" <optional last paragraph>,middleParas = [] <optional middle paragraphs(separate each para with newline into string)>
        '''
        try:
            S = self.S.getSentimentScore(text)
            print ("Sentiment Text : " + text + " ||| SentimentScore[-5 to 5]: " + str(S))
            return S
        except Exception as err:
            print err
            return 0

    def getTopics(self, text):
        text=text.decode('utf-8')
        # print text
        cat = self.catz.getCategory(text)
        print ("Topic Text : " + text + " ||| Topic: " + cat)
        res = cat.split('|')
        return res

    def getKeywords(self, text):
        text=text.decode('utf-8')
        print "Keyword Text",text
        keywords = self.catz.getKeywords(text)
        return keywords


class SentimentHandler(tornado.web.RequestHandler):
    def __init__(self,interface):
        self.interface=interface
    def get(self):
        pass

    def getResults(self,text):
        results = []
        print "inside"
        sentences=text.split(". ")
        for s in sentences:
            print "dfdf: ",s
            t={}
            t["sentence"] = s
            t["score"] = self.S.getSentimentScore(s)
            print str(t)
            results.append(t)
        return results

    def post(self):
        body_string=str(self.request.body)
        response_string=None
        request_obj=None
        results = []
        #if "/json" in self.request.headers["Content-Type"]:
        try:
            request_obj = json.loads(body_string)
            results = self.getResults(request_obj["text"])
            response_dict = {}
            response_dict["status"] = "PASS"
            response_dict["result"] = results
            response_string=json.dumps(response_dict)
        except Exception:
            response_string={"status": "FAIL", "message": "Bad Content Type. Currently only Content-Type application/json supported."}
        # else:
        #     response_string={"status":"FAIL","message":"Bad Content Type. Currently only application/json supported."}
        self.write(response_string)



#
# class TopicHandler(tornado.web.RedirectHandler):
#     def __init__(self):
#         self.log = {}
#         self.catz = Categorize.Categorize()
#     def get(self):
#         pass

class Application(tornado.web.Application):
    def __init__(self):
        pyInterface = PyInterfaceServer()
        handlers = [
            (r"/api/sentiment", SentimentHandler(pyInterface)),
            # (r"/api/topic", TopicHandler),
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
    print "Server started at Port ",SERVER_PORT
    tornado.ioloop.IOLoop.instance().start()

if __name__ == "__main__":
    main()