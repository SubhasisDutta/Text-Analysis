/**
 * Created by Subhasis on 6/1/2016.
 */
var env = process.env.NODE_ENV = process.env.NODE_ENV || 'development';
var config = require('../config/config')[env];
var request = require('request');

exports.getEntityResults = function(req, res) {
    var query = req.body.text;
    var url = config.entity_server_ws;
    request({
        url: url, //URL to hit
        method: 'POST',
        //Lets post the following key/values as form
        form: {
            text: query
        }
    }, function(error, response, body){
        if(error) {
            console.log(error);
            res.send(body);
        } else {
            //console.log(response.statusCode, body);
            var b = JSON.parse(body)[0];
            //console.log(b);
            res.send(b);
        }
    });
};

exports.getTopicResults = function(req, res) {
    var query = req.body.text;
    //console.log(query);

    var url = config.topic_server_ws;
    request({
        url: url, //URL to hit
        method: 'POST',
        json: {
            text: query
        }
    }, function(error, response, body){
        if(error) {
            console.log(error);
            res.send(body);
        } else {
            res.send(body);
        }
    });
};

exports.getSentimentResults = function(req, res) {
    var query = req.body.text;
    //console.log(query);

    var url = config.sentiment_server_ws;
    request({
        url: url, //URL to hit
        method: 'POST',
        json: {
            text: query
        }
    }, function(error, response, body){
        if(error) {
            console.log(error);
            res.send(body);
        } else {
            //console.log(response.statusCode, body);
            //var b = JSON.parse(body);
            //console.log(body);
            res.send(body);
        }
    });
};

var sentimentLevel ={
    "types": [
        {
            "level": "Extremely Negative",
            "color": "#B71C1C",
            "score": -5
        },
        {
            "level": "Highly Negative",
            "color": "#D32F2F",
            "score": -4
        },
        {
            "level": "Very Negative",
            "color": "#F44336",
            "score": -3
        },
        {
            "level": "Negative",
            "color": "#E57373",
            "score": -2
        },
        {
            "level": "Bad",
            "color": "#FFCDD2",
            "score": -1
        },
        {
            "level": "Normal",
            "color": "#FFFFFF",
            "score": 0
        },
        {
            "level": "Good",
            "color": "#C8E6C9",
            "score": 1
        },
        {
            "level": "Very Good",
            "color": "#81C784",
            "score": 2
        },
        {
            "level": "Positive",
            "color": "#4CAF50",
            "score": 3
        },
        {
            "level": "Excellent",
            "color": "#388E3C",
            "score": 4
        },
        {
            "level": "Amazing",
            "color": "#1B5E20",
            "score": 5
        }
    ]
};

exports.getSentimentLevel = function(req, res) {
    res.send(sentimentLevel);
};