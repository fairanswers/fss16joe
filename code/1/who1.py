import utest
import urllib2
import httplib

@utest.ok
def _okHttp():
  "Can I get to the internet?"
  response = urllib2.urlopen('http://www.example.com/')
  print type(response)
  assert response.getcode() == 200, "Should be able to find example.com."
  
@utest.ok  
def _badHttp():
    "Bad HTTP Request. Need to figure out how to handle errors and declare variables of a certian type so I can see them after the try."
    conn = httplib.HTTPSConnection("BadSite123123123123.com", 80, timeout=1)
    conn.request("GET", "/")
    r1 = conn.getresponse()
    print " Status %s" % r1.status
    assert response.getcode() == 404, 'Should have gotten a 404 error.  Instead got %s ' % str(response.getcode() )
    #     working = true
    # if(working == false):
    #     fail("Should have gotten an error")
        
