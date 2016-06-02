/**
 * Created by Subhasis on 6/1/2016.
 */
var env = process.env.NODE_ENV = process.env.NODE_ENV || 'development';
var config = require('../config/config')[env];
var request = require('request');

exports.getEntityResults = function(req, res) {
    var query = req.body.text;
    //console.log(query);

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
            console.log(b);
            res.send(b);
        }
    });
};