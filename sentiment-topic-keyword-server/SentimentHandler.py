import tornado.web
import tornado.httpserver

from tornado.escape import json_encode
from SentimentService import SentimentService
import json

sentimentService = SentimentService()

class SentimentHandler(tornado.web.RequestHandler):
    def getResults(self,text):
        results = []
        print "inside results"
        sentences=text.split(". ")
        for s in sentences:
            print "Sentence: ",s
            t={}
            t["sentence"] = s
            t["score"] = sentimentService.getSentimentScore(s)
            print str(t)
            results.append(t)
        return results

    def get(self):
        text=self.get_argument("text")
        results = self.getResults(text)
        response_dict = {}
        response_dict["status"] = "PASS"
        response_dict["result"] = results
        self.write(json_encode(response_dict))
        self.set_header("Content-Type", "application/json")

    def post(self):
        print "Into Sentiment POST"
        body_string=str(self.request.body)
        request_obj=None
        results = []
        #if "/json" in self.request.headers["Content-Type"]:
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
        # else:
        #     response_string={"status":"FAIL","message":"Bad Content Type. Currently only application/json supported."}
        self.set_header("Content-Type", "application/json")
