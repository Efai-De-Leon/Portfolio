import cgi
form = cgi.FieldStorage()                                                                   #cgi function to "GET" the form

import pymysql

db = pymysql.connect(host='127.0.0.1',
                     user='gallery',
                     password='eecs118',
                     db='gallery')


newGalleryName = form.getvalue('Name')
newGalleryDescription = form.getvalue('description')
newArtist = form.getvalue('artist')
newArtistBirthYear =  form.getvalue('artist_birthyear')
newArtistCountry = form.getvalue('artist_country')
newArtistDescription = form.getvalue('artist_description')

def createArtist():
    print("<form action='' method ='POST'>")
    print("Name:<br>")
    print("<input id = 'fieldname' type='text' name='artist' >")
    print("<br>")
    print("Birth Year:<br>")
    print("<input id = 'fieldname' type='text' name='artist_birthyear' >")
    print("<br>")
    print("Country:<br>")
    print("<input id = 'fieldname' type='text' name='artist_country' >")
    print("<br>")
    print("Description:<br>")
    print("<input id = 'fieldname' type='text' name='artist_description' >")
    print("<br>")
    print("<br><br>")
    print("<input type='submit' value='Submit' id='update_button'>")
    print("</form>")


def createGallery():
    print("<form action='' method ='POST'>")
    print("Name:<br>")
    print("<input id = 'fieldname' type='text' name='Name' >")
    print("<br>")
    print("Description:<br>")
    print("<input id = 'fieldname' type='text' name='description' >")
    print("<br>")
    print("<br><br>")
    print("<input type='submit' value='Submit' id='update_button'>")
    print("</form>")

def executeSQL():
    if (newGalleryName and newGalleryDescription):
        anothercursor = db.cursor()
        sql=("""INSERT IGNORE INTO gallery.gallery(name,description) VALUES (%s,%s)""")
        val = ( newGalleryName, newGalleryDescription)
        anothercursor.execute(sql,val)
        db.commit()
        print("<script>")
        print("window.location.replace('http://localhost:8080/test/cgi-bin/mp3.py');")
        print("</script>")
    if (newArtist):
        anothercursor = db.cursor()
        sql=("""INSERT IGNORE INTO gallery.artist(name, birth_year, country, description) VALUES (%s,%s,%s,%s)""")
        val = ( newArtist, newArtistBirthYear, newArtistCountry, newArtistDescription)
        anothercursor.execute(sql,val)
        db.commit()
        print("<script>")
        print("window.location.replace('http://localhost:8080/test/cgi-bin/mp3.py');")
        print("</script>")

print("Content-Type: text/html\r\n\r\n")
print()
print("<html>")
print("<head>")
executeSQL()
print("<TITLE> Gallery </TITLE>")
print("</head>")
print("<body>")
print("<nav id = 'navi'>")
print("<a  href = '/test/cgi-bin/mp3.py'>Gallery</a>")
print("<a class = 'current' href = '/test/cgi-bin/creategallery.py'>  Create gallery</a>")
print("<a href = '/test/cgi-bin/search.py'> Search </a>")
print("</nav>")
print("<div id = 'creategallerywrapper'>")
print("<div id = 'creategallerydiv'>")
print("<p class = 'titlepage'>")
print("Create Gallery: ")
print("</p>")
createGallery()
print("</div>")
print("</div>")
print("Create Artist")
createArtist()
print("<script>")
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
print("a:hover{ color: #00fff5;}")
print("button{ background-color: #4682B4; border: none; color: white; text-align: center; text-decoration: none; cursor: pointer; width: 100%; margin-bottom: 20px; height: 50px; display: flex; justify-content: center; align-items: center; padding: 0;}")
print(".buttontext{ text-overflow: ellipsis; width: 80%; overflow: hidden; white-space: nowrap; display: block;}")
print("button:hover{opacity: 0.8; border: none; color: white; text-align: center; text-decoration: underline; cursor: pointer;}")
print("button:focus {outline:0;}")
print(".select-selected{background-color: DodgerBlue;}")

print("#creategallerydiv{background-color: #393e46; margin: 0px;}")
print("#creategallerywrapper{}")
print(".titlepage{margin: 4px; padding: 0px;}")
print("#update_button{ width: auto; font-size: 16px; background-color: #00adb5; color: white; cursor: pointer; border: none;}")
print("#update_button:{opacity: 0.8;}")
print("</style>")



print("</html>")
db.close()
