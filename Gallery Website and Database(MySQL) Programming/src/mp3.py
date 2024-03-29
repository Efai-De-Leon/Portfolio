#!/usr/bin/env python3 
import cgi
form = cgi.FieldStorage()                                                                   #cgi function to "GET" the form

import pymysql

db = pymysql.connect(host='127.0.0.1',
                     user='root',
                     password='hormigero',
                     db='gallery')

anothercursor = db.cursor()
sql="SELECT gallery_id FROM gallery"
anothercursor.execute(sql)


newGalleryTitle = form.getvalue('Title')
newGalleryDescription = form.getvalue('Description')
galleryID = form.getvalue('galleryid')

def galleryfunction():
    for row in anothercursor.fetchall(): #cur.afetchone()
        b = "?var=" + str(row[0])
        link = "/test/cgi-bin/images.py" + b
        anothercur = db.cursor()
        sql="SELECT name, description FROM gallery WHERE gallery_id = " + str(row[0])
        anothercur.execute(sql)
        for info in anothercur.fetchall():
            print("<a href = '", link,"'>")
            print("<div class = 'agallery'>")
            print("<h3>")
            print("<form action='' method ='POST' id ='form'>")
            print("Title: ")
            print("</a>")
            print("<input id = 'fieldname' type='text' name='Title' value = '",str(info[0]),"' required>")
            print("<a href = '", link,"'>")
            print("</h3>")
            print("<h4>")
            print("Description: ")
            print("</a>")
            print("<br>")
            print("<input id = 'galleryid' type='hidden' name='galleryid' value=",row[0],">")
            print("<input id = 'fieldname' type='text' name='Description' value = '",str(info[1]),"' required>")
            print("<input type='submit' id = 'update_button' value='Edit'>")
            print("</form>")
            print("<a href = '", link,"'>")
            print("OPEN")
            print("</h4>")
            print("</a>")
            print("</div>")

def executeSQL():
    if (newGalleryTitle or newGalleryDescription):
        cur = db.cursor()
        sql="UPDATE gallery SET name = '"+ str(newGalleryTitle) +"',description = '"+str(newGalleryDescription) +"' WHERE gallery_id = "+ str(galleryID)
        cur.execute(sql)
        db.commit()

print("Content-Type: text/html\r\n\r\n")
print()
print("<html>")
print("<head>")
executeSQL()
print("<TITLE> Gallery </TITLE>")
print("<body>")
print("</head>")
print("<nav id = 'navi'>")
print("<a class = 'current' href = '/test/cgi-bin/mp3.py'>Gallery</a>")
print("<a href = '/test/cgi-bin/creategallery.py'>  Create gallery</a>")
print("<a href = '/test/cgi-bin/search.py'> Search </a>")
print("</nav>")
print("<div id = 'mp3wrapper'>")
print("<div id = 'galleryselection'>")
print("<p id = 'thiss'>")
print("Galleries: ")
print("</p>")
galleryfunction()
print("</div>")
print("<div id = 'greeting'></div>")
print("</div>")

print("<script>")
print("var x = new Date();")
print("var hours = x.getHours();")
print("var y;")
print("if(hours < 12){")
print("y = 'Good Morning';")
print("}")
print("if(hours >=12 && hours <=17){")
print("y = 'Good Afternoon';")
print("}")
print("if(hours >=17 && hours <=24){")
print("y = 'Good Evening';")
print("}")
print("document.getElementById('greeting').innerHTML='<br>'+ y +'<br>'")
print("</script>")
print("</body>")
print("<style>")
print("body{background-color: #222831; color: white; min-width: 1270px;}")
print("#navi{background-color: #393e46;display: grid; grid-template-columns: 1fr 1fr 1fr; font-family: Arial, Helvetica, sans-serif; margin-bottom: 10px}")
print("#navi > p{margin: 0; padding: 5px; text-align: center;}")
print("#navi > p:hover{background-color: #00adb5;}")
print("#navi > a{margin: 0; padding: 5px; text-align: center;}")
print("#navi > a:hover{background-color: #00adb5; color: white;}")
print(".current{background-color: #00adb5;}")
print("#wrapper{display: grid; grid-template-columns: 1fr 1fr;}")
print("#thumbnailssection{margin-right: 4px; overflow: auto; padding: 4px;width: 636px;}")
print("#thumbnailwrapper{ grid-column: 1/2; margin: 0; padding: 0; display: flex; justify-content: center;}")
print("#galleryselection{background-color: #393e46; overflow: auto; height: 700px;}")
print("#bigpicdisplay{ width: 100%; height: 1000px;}")
print("#somethings{ background-color: #393e46; margin-left: 4px; padding: 4px;}")
print("a{ text-decoration: none; color: white;}")
print("a:hover{ color: #00fff5; opacity: 0.8;}")
print("button{ background-color: #4682B4; border: none; color: white; text-align: center; text-decoration: none; cursor: pointer; width: 100%; margin-bottom: 20px; height: 50px; display: flex; justify-content: center; align-items: center; padding: 0;}")
print(".buttontext{ text-overflow: ellipsis; width: 80%; overflow: hidden; white-space: nowrap; display: block;}")
print("button:hover{ border: none; color: white; text-align: center; text-decoration: underline; cursor: pointer;}")
print("button:focus {outline:0;}")
print(".select-selected{background-color: DodgerBlue;}")
print(".agallery{ background-color:  #EF820D;  width: 310px; margin: 4px; float: left;}")
print("h3{text-align: center;}")
print("h4{background-color: #D7722C; margin: 0px; padding: 6px; margin-bottom: 15px }")
print("#thiss{margin: 4px;}")
print("#greeting{justify-content: center; margin: auto; font-size: 90px;}")
print("#mp3wrapper{grid-template-rows: auto auto; display: grid; }")
print("#update_button{width: 20%; font-size: 16px; background-color: #00adb5; color: white; cursor: pointer; border: none;}")
print("#update_button:hover{opacity: 0.8;}")
print("#fieldname{text-align: left; justify-content: left; display: inline-block; margin: auto;}")
print("#form{justify-content: left;}")
print("</style>")
print("</html>")
db.close()
