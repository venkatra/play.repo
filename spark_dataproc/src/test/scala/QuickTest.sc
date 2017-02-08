
val x = "URL     ftp://aden.princeton.edu/pub/lyo/iwmo3/training-class/pom_v16.docx"
x.replaceAll("URL( )+"," ").trim

val mx ="MENTION rotation axis   33718   http://en.wikipedia.org/wiki/Rotation"
val my = mx.replaceAll("[0-9]"," ").replaceAll("( )+",";")
val url = my.split(";").last
my.split(";").filter(x => {x.equals(url) == false}).map( (_ ,url) ).toSeq
  //.replaceAll("-","").trim.split(";").toSeq.slice(0,my.length)


val z = "TOKEN   operates        358936"
z.replace("TOKEN"," ").replaceAll("[0-9]"," ").trim.replaceAll("( )+",";")


