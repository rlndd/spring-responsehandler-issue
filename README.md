ResponseEntity returns our expected Cache-Control header
```
curl -v 'http://localhost:8080/responseEntity'
*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
> GET /responseEntity HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.64.1
> Accept: */*
>
< HTTP/1.1 200 OK
< Cache-Control: max-age=3600
< Content-Type: text/plain;charset=UTF-8
< Content-Length: 21
<
* Connection #0 to host localhost left intact
supposed to be cached* Closing connection 0
```

ServerResponse does not override default Cache-Control header
```
curl -v 'http://localhost:8080/serverResponse'
*   Trying ::1...
* TCP_NODELAY set


* Connected to localhost (::1) port 8080 (#0)
> GET /serverResponse HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.64.1
> Accept: */*
>
< HTTP/1.1 200 OK
< Cache-Control: no-cache
< Content-Type: text/plain;charset=UTF-8
< Content-Length: 21
<
* Connection #0 to host localhost left intact
supposed to be cached* Closing connection 0
```