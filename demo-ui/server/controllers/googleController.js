/**
 * Created by Subhasis on 4/22/2016.
 */
var env = process.env.NODE_ENV = process.env.NODE_ENV || 'development';
var config = require('../config/config')[env];
var request = require('request');


exports.getGoogleSearchResults = function(req, res) {
    var query = req.params.query;
    var url = config.google_query_URL+query+"&cx="+config.google_search_cx+"&key="+config.google_search_key;
    console.log(url);
    request(url, function (error, response, body) {
        //console.log(error);
        //console.log(body);
        res.send(body);
    });
};

