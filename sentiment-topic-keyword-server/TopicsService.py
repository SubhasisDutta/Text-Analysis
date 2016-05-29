
from Topics import Categorize

class TopicsService:
     def __init__(self):
         self.log = {}
         self.catz = Categorize.Categorize()

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