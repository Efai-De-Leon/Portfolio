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

id = form.getvalue('var')
idstring = str(id)

newEditYear = form.getvalue('Year')
newEditType = form.getvalue('Type')
newEditWidth = form.getvalue('Width')
newEditHeight = form.getvalue('Height')
newEditLocation = form.getvalue('Location')
newEditDescription = form.getvalue('Description')
newEditTitle = form.getvalue('Title')
newEditLink = form.getvalue('Link')
newEditArtistBirthYear = form.getvalue('birthYear')
newEditArtistCountry = form.getvalue('Country')
newEditArtistDescription = form.getvalue('Description')
artistIDForm = form.getvalue('artistidfromform')

def artist(artistid):
    anothercur = db.cursor()
    sql="SELECT birth_year, country, description FROM artist WHERE artist_id = " + str(artistid)
    anothercur.execute(sql)
    artistDictionary = ["Year of birth:  ", "Country:  ", "Description: "]
    nameDictionary = ["birthYear" ,"Country", "Description"]
    for info in anothercur.fetchall():
        print("<form class = 'artistdetailform' action='' method ='POST'>")

        print("<label>")
        print("Year of birth: ")
        print("</label>")

        print("<input id =  'fieldname_artist' type = 'text' name = 'birthYear' value = '",str(info[0]),"' required>")
        print("<br>")

        print("<label>")
        print("Country: ")
        print("</label>")
        print("<input id =  'fieldname_artist' type = 'text' name = 'Country' value = '",str(info[1]),"' required>")
        print("<br>")

        print("<label>")
        print("Description: ")
        print("</label>")
        print("<input id =  'fieldname_artist' type = 'text' name = 'Description' value = '",str(info[2]),"' required>")
        print("<br>")


        #for x in range(len(info)):
        #    print("<label>")
        #    print(artistDictionary[x])
        #    print("</label>")
        #    print("<input id = 'fieldname_artist' type='text' name=",nameDictionary[x]," value = ",info[x]," required>")
        #    print("<br>")
        print("<input id = 'fieldname' type='hidden' name='artistidfromform' value=",artistid,">")
        print("<br>")
        print("<input type='submit' id='edit_artist_button' value='Edit artist'>")
        print("</form>")

def picture(imgid):
    anothercur = db.cursor()
    #if imgid != None:
    sql="SELECT title, link FROM image WHERE image_id = " + str('NULL')
    #    print('here')
    anothercur.execute(sql)
    for info in anothercur.fetchall():
        print("<form action='' id = 'title_form' method ='POST'>")
        print("<h3>")
        print("Title: ")
        print("<input id = 'fieldname_title' type='text' name='Title' value = '",str(info[0]),"' required>")
        print("</h3>")
        print("<br>")
        print("<h4>")
        print("Link: ")
        print("<input id = 'linkfield' type='text' name='Link' value = ",info[1]," required>")

        print("<input type='submit' id='submit_edit_button' value='Edit'>")
        print("</h4>")
        print("</form>")
        print("<div id = 'imgdiv'>")
        print("<img class 'picture' src='",info[1] ,"' width = '100%' alt = '",info[0],"'>")
        print("</div>")
        print("<br>")
    anothercur2 = db.cursor()
    #sql="SELECT year, type, width, height, location, description FROM detail WHERE image_id = " + str(imgid)
    anothercur2.execute(sql)
    print("<form action='' method ='POST'>")
    someDes = ['Year', 'Type', 'Width', 'Height', 'Location', 'Description']
    for detailrow in anothercur2.fetchall():
        for y in range(len(detailrow)):
            print("<label>")
            print(someDes[y] + ": &nbsp;")
            print("</label>")
            print("<input id = 'fieldname_description' type='text' name=",someDes[y]," value = '",str(detailrow[y]),"' required>")
            print("<br>")

        print("<br>")
        print("<br>")
        print("<input id = 'submit_edit_button2' type='submit' value='Edit description'>")
        print("</form>")
            #pictureInfo.append(detailrow[y])
    anothercur3 = db.cursor()
    #sql="SELECT artist_id FROM image WHERE image_id = " + str(imgid)
    anothercur3.execute(sql)
    for artistid in anothercur3.fetchall():
        anothercur4 = db.cursor()
     #   sql="SELECT name FROM artist WHERE artist_id = " + str(artistid[0])
        anothercur4.execute(sql)
        for artistrow in anothercur4.fetchall():
            print("<label>")
            print("Artist: &nbsp;")
            print("</label>")
            srcstring =  str(artistid[0])
            print("<button onclick ='showArtistInfo()'>",artistrow[0],"</button>")
        print("<p class = 'hiddendes' >")
        artist(artistid[0])
        print("</p>")


def executeSQL():
    if (newEditYear):
        anothercursor = db.cursor()
        sql="UPDATE detail SET year = '"+ str(newEditYear) +"',type = '"+str(newEditType)  +"',width='"+str(newEditWidth)  +"',height='"+str(newEditHeight) +"',location='"+str(newEditLocation)  +"',description='"+str(newEditDescription)+ "' WHERE image_id = "+idstring
        anothercursor.execute(sql)
        db.commit()

    if (newEditTitle or newEditLink):
        anothercursor = db.cursor()
        sql="UPDATE image SET title = '"+ str(newEditTitle) +"',link = '"+str(newEditLink) +"' WHERE image_id = "+idstring
        anothercursor.execute(sql)
        db.commit()
    if (newEditArtistCountry or newEditArtistBirthYear or newEditArtistDescription):
        anothercursor = db.cursor()
        sql="UPDATE artist SET birth_year = '"+ str(newEditArtistBirthYear) +"',country = '"+str(newEditArtistCountry) + "',description='"+str(newEditArtistDescription)+  "' WHERE artist_id = "+str(artistIDForm)
        anothercursor.execute(sql)
        db.commit()


print("Content-Type: text/html\r\n\r\n")
print()
print("<html>")
print("<head>")
executeSQL()
print("<TITLE> Gallery </TITLE>")
print("</head>")
print("<body>")
print("<div id = 'wrapper'>")
picture(idstring)
print("</div>")
print("</body>")
print("<script>")
print("function showArtistInfo(){")
print("var x = document.getElementsByClassName('artistdetailform');")
print("x[0].style.visibility = 'visible';")
print("}")
print("</script>")
print("<style>")
print("body{ margin: 0; color: white; }")
print("#wrapper{margin-left: 4px; background-color: #393e46; padding: 4px;}")
print("h3{padding: 0px; margin: 0px; text-align: center;}")
print("h4{padding: 0px; margin: 0px; text-align: center;}")
print("#imgdiv{width: 500px; height: 500px; background-color: black; margin: auto; display: flex; justify-content: center; align-items: center;}")
print(".picture{margin: auto;}")
print("a{text-decoration: none; color: white;}")
print("a:hover{text-decoration:underline;}")
print(".artistdetailform{visibility: hidden; }")
print("#linkfield {width: 500px;}")
print("label{display: inline-block; float: left; clear: left; text-align:left; width: 100px;}")
print("#fieldname_description{display: inline-block; float: left;}")
print("#fieldname_artist{display: inline-block; float: left;}")
print("#fieldname_title{text-align: center;}")
print("#linkfield{display: inline-block; float: center;}")
print("#title_form{justify-content: center;}")
print("#submit_edit_button{ padding-bottom: 2.5px; width: 10%; font-size: 16px; background-color: #00adb5; color: white; cursor: pointer; border: none;}")
print("#submit_edit_button2{width: 35.4%; font-size: 16px; background-color: #00adb5; color: white; cursor: pointer; border: none;}")
print("#submit_edit_button:hover{opacity: 0.8;}")
print("#submit_edit_button2:hover{opacity: 0.8;}")
print("#edit_artist_button{width: 35.4%; font-size: 16px; background-color: #00adb5; color: white; cursor: pointer; border: none;}")
print("#edit_artist_button:hover{opacity: 0.8;}")

print("button{width: 22%; font-size: 16px; background-color: #d4af37; color: white; cursor: pointer; border: none;}")
print("button:hover{opacity: 0.8;}")

print("</style>")
print("</html>")

db.close()
