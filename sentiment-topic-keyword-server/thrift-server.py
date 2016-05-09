# -*- coding: utf-8 -*-
'''
Copyright 2015 Serendio Inc.
Author - Satish Palaniappan

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and limitations under the License.
'''
#from atk import Text


from IPython.core.release import keywords
__author__ = "Satish Palaniappan, Praveen Jesudhas"


import sys,traceback
sys.path.append("../")

from ThriftWrapper import PyInterface
from Sentiment.SentiHandlers.SentiMaster import SentiHandle
from Topics import Categorize

from ThriftWrapper.ttypes import *
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer
import socket


class PyInterfaceServer:
    def __init__(self):
        self.log = {}
        self.S = SentiHandle()
        self.catz = Categorize.Categorize()

    def ping(self):
        print ("Ping Success !! :D")
        return

    def getSentimentScore(self, obj):
       
        '''
        Arguments List:
        general -> mainText,textType = "general"
        microblogs -> mainText, textType = "microblogs"
        comments -> mainText, textType = "comments"
        reviews -> mainText, textType = "reviews", title = "" <optional>,topDomain,subDomain = "" <depends, not always optional, refer the list in config.py>
        blogs_news -> mainText< or first paragraph>, title, textType="blogs_news",lastPara = "" <optional last paragraph>,middleParas = [] <optional middle paragraphs(separate each para with newline into string)>
        '''
        try:
            S = self.S.getSentimentScore(obj.mainText,obj.textType,obj.title,obj.middleParas,obj.lastPara,obj.topDomain,obj.subDomain)
            print ("Sentiment Text : " + obj.mainText + " ||| SentimentScore[-5 to 5]: " + str(S))
            return S
        except Exception as err:
            print(traceback.format_exc())
            print(sys.exc_info()[0])
            print err

    def getTopics(self, text):
        text=text.decode('utf-8')
        print text
        cat = self.catz.getCategory(text)
        print ("Topic Text : " + text + " ||| Topic: " + cat)
        res = cat.split('|')
        return res
   
    def getKeywords(self, text):
        text=text.decode('utf-8')
        print "Keyword Text",text
        keywords = self.catz.getK The Union government on Thursday proposed to strip the Reserve Bank Governors veto vote on
 Indias monetary policy. The government also proposed to grant itself the power to appoint four of t
he six members of the Monetary Policy Committee, whose remit will include decisions on setting inter
est rates to maintain inflation at the targeted level. The revised draft of the Indian Financial Cod
e, put out by the Union Finance Ministry for comments, proposes that the Reserve Bank Chairperson sh
all head the committee, with no reference to the Governor. It is not clear from the draft if a re-de
signation is planned. An earlier draft had proposed to give the Governor the right to overrule the m
onetary policy committee decision. If the inflation target is not met, then the Reserve Bank will ha
ve to explain the reasons and propose remedial actions. Under the revised draft, the non-government
members of the committee are to be drawn from the Reserve Bank. The Reserve Banks Board will nominat
e one of its executives as the fifth member of the committee. The Chairperson will nominate one of i
ts employees as the sixth member. The move comes in the wake of a severe breakdown of talks between
the Centre and the Reserve Bank over amendments to the RBI Act, which Finance Minister Arun Jaitley
had announced in his Budget speech.eywords(text)
        return keywords 
         

port = '19090'
handler = PyInterfaceServer()
processor = PyInterface.Processor(handler)
transport = TSocket.TServerSocket(port=port)
tfactory = TTransport.TBufferedTransportFactory()
pfactory = TBinaryProtocol.TBinaryProtocolFactory()

server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)

print "Python Interface server for Sentiment, Topics and Keywords started on port "+port+"..."
server.serve()
