import tornado.web
import tornado.httpserver
import json
import re
from tornado.escape import json_encode
from TopicsService import TopicsService

topicsService = TopicsService()

class TopicsHandler(tornado.web.RequestHandler):
    def getResults(self,text):
        # print "inside topic results"
        # print "Text: ",text
        t={}
        t["text"] = text
        text = re.sub('[^A-Za-z0-9]+', ' ', text)
        t["topics"]=topicsService.getTopics(text)
        t["keywords"]=topicsService.getKeywords(text)
        # print str(t)
        return t

    def get(self):
        # print "Into Topic GET"
        text=self.get_argument("text")
        results = self.getResults(text)
        response_dict = {}
        response_dict["status"] = "PASS"
        response_dict["result"] = results
        self.write(json_encode(response_dict))
        self.set_header("Content-Type", "application/json")

    def post(self):
        # print "Into Topics POST"
        body_string=str(self.request.body)
        request_obj=None
        results = []
        try:
            request_obj = json.loads(body_string)
            results = self.getResults(request_obj["text"])
            response_dict = {}
            response_dict["status"] = "PASS"
            response_dict["result"] = results
            self.write(json_encode(response_dict))
        except Exception:
            response_string={"status": "FAIL", "message": "Bad Content Type. Currently only Content-Type application/json supported."}
            self.write(response_string)
        self.set_header("Content-Type", "application/json")