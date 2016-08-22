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
    "Bad HTTP Request. Had to figure out how to set r1 to None so I can check it in the original scope."
    #Here, I want to set httplib.HTTPResponse r1 = null but that's not right
    r1 = None
    try:
      conn = httplib.HTTPSConnection("BadSite123123123123.com", 80, timeout=1)
      conn.request("GET", "/")
      print "About to do get"
      r1 = conn.getresponse()
      fail("Should have gotten an error")
    except Exception,e:
      #Do something
      print e
      1==1
    assert r1 is None
#      assert r1.getcode() != 200 #throws UnboundLocalError: local variable 'r1' referenced before assignment
#    print " Status %s" % r1.status #throws UnboundLocalError: local variable 'r1' referenced before assignment
    
        
