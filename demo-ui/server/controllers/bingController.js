/**
 * Created by Subhasis on 4/22/2016.
 */
var env = process.env.NODE_ENV = process.env.NODE_ENV || 'development';
var config = require('../config/config')[env];


exports.getBingSearchResults = function(req, res) {
    var query = req.params.query;
    var acctKey = config.bing_account_key;
    var auth    = new Buffer([ acctKey, acctKey ].join(':')).toString('base64');

    var request = require('request').defaults({
        headers : {
            'Authorization' : 'Basic ' + auth
        }
    });
    request.get({
        url : config.bing_query_URL ,
        qs  : {
            $format : 'json',
            Query   : "'" + query + "'", // the single quotes are required!
        }
    }, function(err, response, body) {
        //console.log(body);
        if (err)
            return res.send(500, err.message);
        if (response.statusCode !== 200)
            return res.send(500, response.body);
        //console.log(response.body);
        //var results = JSON.parse(response.body);
        //console.log(results);
        res.send(response.body);
    });

};