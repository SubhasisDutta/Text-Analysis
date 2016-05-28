Install Tornado
pip install tornado

In order to extract Sentiments, Topics and Keywords. The Tornado Server runs on 8888.

To run this please ensurre the folder nltk_data is acessible. It contains a dependency of stopwords.

Before running ensure all the dependencies listed in requirements.txt are installed.

If running on Windows use packages like Anaconda to install all dependency.

To test if every thing is working 

You should get the  message from server.

Also you can run the client.py to test.

From Python Console type
execfile("server.py");


easy_install -U gensim
apt-get install python-setuptools python-dev build-essential
easy_install pip
apt-get remove python-numpy
apt-get install python python-dev libatlas-base-dev gcc gfortran g++
pip uninstall numpy
pip install numpy
pip install scipy

Test Input
{"text":"The Union government on Thursday proposed to strip the Reserve Bank Governors veto vote on Indias monetary policy. The government also proposed to grant itself the power to appoint four of the six members of the Monetary Policy Committee, whose remit will include decisions on setting interest rates to maintain inflation at the targeted level. The revised draft of the Indian Financial Code, put out by the Union Finance Ministry for comments, proposes that the Reserve Bank Chairperson shall head the committee, with no reference to the Governor. It is not clear from the draft if a re-designation is planned. An earlier draft had proposed to give the Governor the right to overrule the monetary policy committee decision. If the inflation target is not met, then the Reserve Bank will have to explain the reasons and propose remedial actions. Under the revised draft, the non-government members of the committee are to be drawn from the Reserve Bank. The Reserve Banks Board will nominate one of its executives as the fifth member of the committee. The Chairperson will nominate one of its employees as the sixth member. The move comes in the wake of a severe breakdown of talks between the Centre and the Reserve Bank over amendments to the RBI Act, which Finance Minister Arun Jaitley had announced in his Budget speech."}