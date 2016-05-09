/**
 * Created by Subhasis on 4/22/2016.
 */
var env = process.env.NODE_ENV = process.env.NODE_ENV || 'development';
var config = require('../config/config')[env];
var request = require('request');


exports.getQuerySearchResults = function(req, res) {
    var query = req.params.query;

    var url = config.search_query_ws+query;
    console.log(url);
    request(url, function (error, response, body) {
        //console.log(error);
        //console.log(body);
        res.send(body);
    });
};

exports.getExpansionSearchResults = function(req, res) {
    var query = req.params.query;

    var url = config.query_expansion_ws+query;
    console.log(url);
    request(url, function (error, response, body) {
        //console.log(error);
        //console.log(body);
        res.send(body);
    });
};

exports.getClusterSearchResults = function(req, res) {
    var query = req.params.query;

    var url = config.query_cluster_ws+query;
    console.log(url);
    request(url, function (error, response, body) {
        //console.log(error);
        //console.log(body);
        res.send(body);
    });
};