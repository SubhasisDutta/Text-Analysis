
from Sentiment.SentiHandlers.SentiMaster import SentiHandle

class SentimentService:
    def __init__(self):
        self.log = {}
        self.S = SentiHandle()

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