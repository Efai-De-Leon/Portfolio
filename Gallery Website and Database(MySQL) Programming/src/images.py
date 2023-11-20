import cgi
form = cgi.FieldStorage()                                                                   #cgi function to "GET" the form

import pymysql

db = pymysql.connect(host='127.0.0.1',
                     user='root',
                     password='hormigero',
                     db='gallery')
#cur=db.cursor()
#sql="SELECT * FROM artist"
#cur.execute(sql)
newImageLink = form.getvalue('uimage_link')
newTitle = form.getvalue('uimage_title')
artistName = form.getvalue('uimage_artist')
newYear = form.getvalue('uimage_year')
newType = form.getvalue('uimage_type')
newWidth = form.getvalue('uimage_width')
newHeight = form.getvalue('uimage_height')
newLocation = form.getvalue('uimage_location')
newDescription = form.getvalue('uimage_description')

imageIDtoDelete = form.getvalue('delete_image_id')
detailIDtoDelete = form.getvalue('delete_detail_id')
def uploadImage():
    print("<form action='' method ='POST'>")
    print("<label>")
    print("Title:")
    print("</label>")
    print("<input id = 'fieldtitle' type='text' name='uimage_title' required>")
    print("<br>")
    print("<label>")
    print("Link:")
    print("</label>")
    print("<input id = 'fieldlink' type='text' name='uimage_link' required>")
    print("<br>")
    print("<label>")
    print("Year:")
    print("</label>")
    print("<input id = 'fieldyear' type='text' name='uimage_year' required>")
    print("<br>")
    print("<label>")
    print("Type:")
    print("</label>")
    print("<input id = 'fieldtype' type='text' name='uimage_type' required>")
    print("<br>")
    print("<label>")
    print("Width:")
    print("</label>")
    print("<input id = 'fieldwidth' type='text' name='uimage_width' required>")
    print("<br>")
    print("<label>")
    print("Height:")
    print("</label>")
    print("<input id = 'fieldheight' type='text' name='uimage_height' required>")
    print("<br>")
    print("<label>")
    print("Location:")
    print("</label>")
    print("<input id = 'fieldlocation' type='text' name='uimage_location' required>")
    print("<br>")
    print("<label>")
    print("Description:")
    print("</label>")
    print("<input id = 'fielddescription' type='text' name='uimage_description' required>")
    print("<br>")
    print("<label>")
    print("Artist:")
    print("</label>")
    print("<input id = 'fieldartist' type='text' name='uimage_artist' required>")
    print("<br>")
    print("<br><br>")
    print("<input type='submit' id = 'submit_button' value='Submit'>")
    print("<br>")
    print("</form>")

def executeSQL():
    maxImageID = 0
    newMaxImageID = 0
    maxDetailID = 0
    newMaxDetailID = 0
    artistID = 0
    artistNameString = "'"+str(artistName)+"'"
    cur = db.cursor()

    sql = "SET @@SESSION.information_schema_stats_expiry = 0"
    cur.execute(sql)
    sql="SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'gallery' AND TABLE_NAME = 'image'"
    cur.execute(sql)
    for value in cur.fetchall():
        maxImageID = value[0]

    newMaxImageID = maxImageID
    sql = "SET @@SESSION.information_schema_stats_expiry = 0"
    cur.execute(sql)
    sql="SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'gallery' AND TABLE_NAME = 'detail'"
    cur.execute(sql)
    for detailid in cur.fetchall():
        maxDetailID = detailid[0]
    newMaxDetailID = maxDetailID


    if (newImageLink and newTitle):
        anothercursor = db.cursor()
        sql="SELECT artist_id FROM artist WHERE name= " + artistNameString
        anothercursor.execute(sql)
        for valueID in anothercursor.fetchall():
            artistID = valueID[0]

        sql=("""INSERT IGNORE INTO gallery.image(title,link,gallery_id,artist_id, detail_id) VALUES (%s,%s,%s,%s,%s)""")
        val = ( newTitle, newImageLink, chosenid,artistID, newMaxDetailID)
        anothercursor.execute(sql,val)
    if (newYear and newType and newWidth and newHeight and newLocation and newDescription):
        anothercursor2 = db.cursor()
        sql=("""INSERT IGNORE INTO gallery.detail(image_id,year,type,width,height,location,description) VALUES (%s,%s,%s,%s,%s,%s,%s)""")
        val = ( newMaxImageID, newYear, newType, newWidth, newHeight,newLocation,newDescription)
        anothercursor2.execute(sql,val)
        db.commit()
        print("<script>")
        print("window.location.replace('http://localhost:8080/test/cgi-bin/mp3.py');")
        print("</script>")

    if (imageIDtoDelete and detailIDtoDelete):
        sql="DELETE FROM image WHERE image_id = " + str(imageIDtoDelete)
        cur.execute(sql)
        sql="DELETE FROM detail WHERE detail_id = " + str(detailIDtoDelete)
        cur.execute(sql)
        db.commit()
        print("<script>")
        print("window.location.replace('http://localhost:8080/test/cgi-bin/mp3.py');")
        print("</script>")



chosenid = form.getvalue('var') #userwill pick var
idstring = str(chosenid)

def deleteImageButton(currentimageid, currentdetailid):
    print("<form action='' method ='POST'>")
    print("<input id = 'deleteimageid' type='hidden' name='delete_image_id' value=",currentimageid,">")
    print("<input id = 'deletedetailid' type='hidden' name='delete_detail_id' value=",currentdetailid,">")
    print("<input type='submit' id= 'delete_button' value='Delete'>")
    print("</form>")


def countImages():
    anothercursor5 = db.cursor()
    sql="SELECT image_id FROM image WHERE gallery_id = " + idstring
    anothercursor5.execute(sql)
    number_of_images = 0
    for imageid in anothercursor5.fetchall(): #cur.afetchone()
        number_of_images = number_of_images + 1
    return number_of_images


anothercursor5 = db.cursor()
sql="SELECT image_id FROM image WHERE gallery_id = " + idstring
anothercursor5.execute(sql)
def imagesThumbnails():
    for imageid in anothercursor5.fetchall(): #cur.afetchone()
        anothercur6 = db.cursor()
        sql="SELECT title, link, detail_id FROM image WHERE image_id = " + str(imageid[0])
        anothercur6.execute(sql)
        for info in anothercur6.fetchall():
            print("<div class = 'titleimagelink'>")
            print("<h3>",info[0],"</h3>")
            print("<br>")
            print("<div class = 'imgdiv'>")
            print("<img class = 'picture' src='",info[1] ,"' width = '100%' alt = '",info[0],"'>")
            print("</div>")
            print("<br>")
            print("<button onclick='myFunction(",imageid[0],")'> <span class = 'buttontext'>",info[1],)
            print("</span></button>")
            deleteImageButton(imageid[0],info[2])
            print("</div>")

anothercursor7 = db.cursor()
sql="SELECT image_id FROM image WHERE gallery_id = " + idstring
anothercursor7.execute(sql)
def firstImage():
    for imageid in anothercursor7.fetchall(): #cur.afetchone()
        return imageid[0]

print("Content-Type: text/html\r\n\r\n")
print()
print("<html>")
print("<head>")
executeSQL()
print("<TITLE> Gallery </TITLE>")
print("</head>")
print("<body>")
print("<nav id = 'navi'>")
print("<a class = 'current' href = '/test/cgi-bin/mp3.py'>Gallery</a>")
print("<a href = '/test/cgi-bin/creategallery.py'>  Create gallery</a>")
print("<a href = '/test/cgi-bin/search.py'> Search </a>")
print("</nav>")
print("<div = ultra_wrapper>")
print("<div = 'upload_wrapper'>")
print("Upload Image: ")
uploadImage()
print("</div>")
print("<div id = 'wrapper'>")
x = countImages()
print("Number of Images Found: ", x)
print("<br>")
print("<div id = 'thumbnailwrapper'>")
print("<div id = 'thumbnailssection' >")
anImageId = firstImage()
imagesThumbnails()
srcstring = "/test/cgi-bin/imagedisplay.py?var=" + str(anImageId)
print("</div>")
print("</div>")
print("<iframe id = 'bigpicdisplay' frameBorder = '0'  src = '",srcstring,"'>")
print("</iframe>")
print("</div>")
print(x)
print("</div>")

print("<script>")
print("function myFunction(param){")
print("var str = '/test/cgi-bin/imagedisplay.py?var=' + param.toString()")
print("document.getElementById('bigpicdisplay').src = str;")
print("}")
print("</script>")

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
print("#ultra_wrapper{display: grid; grid-template-rows: 1fr 1fr;}")

print("h3{padding: 0px; margin: 0px; text-align: center;}")
print(".imgdiv{width: 150px; height: 150px; background-color: black; margin: auto; display: flex; justify-content: center; align-items: center;}")
print(".titleimagelink{background-color: #57A0D3; width: 310px; margin: 4px; height: 310px; float: left;}")
print("#upload_wrapper{justify-content: center; display: block;}")
print("label{display: inline-block; float: left; clear: left; text-align:left; width: 100px;}")
print("input{display: inline-block; float: left;}")
print("#delete_button{ width: 100%; font-size: 16px; background-color: #EF820D; color: white; cursor: pointer; border: none;}")
print("#delete_button:hover{opacity: 0.8;}")

print("#submit_button{ width: auto%; font-size: 16px; background-color: #00adb5; color: white; cursor: pointer; border: none;}")
print("#submit_button:hover{opacity: 0.8;}")
print("</style>")

print("</body>")
print("</html>")

db.close()
