import cgi
form = cgi.FieldStorage()                                                                   #cgi function to "GET" the form

import pymysql

db = pymysql.connect(host='127.0.0.1',
                     user='gallery',
                     password='eecs118',
                     db='gallery')


def searchFunction():
    print("<form action='' method ='POST'>")
    print("")
    print("Search Images by ")
    print("<div class = 'custom-select' >")
    print("<select id = 'searchBy' name = 'searchBy' onchange='showSearchBar()'>")
    print("<option value = 'selectSomething'>-Select-</option>")
    print("<option value = 'Type'>Type</option>")
    print("<option value = 'RangeofYear'>Range of Year</option>")
    print("<option value = 'ArtistName'>Artist Name</option>")
    print("<option value = 'Location'>Location</option>")
    print("</select>")
    print("</div>")
    print("<br><br>")
    print("<div class = 'type' >")
    print("<label>")
    print("Search: ")
    print("</label>")
    print("<input id = 'fieldname' type='text' name='searchbar' >")
    print("<input type='submit' value='Submit' id ='submit_button'>")
    print("</div>")
    print("<div class = 'RangeofYear'>")
    print("<label>")
    print("Initial Year: ")
    print("</label>")
    print("<input id = 'fieldname' type='text' name='year1' >")
    print("<br>")
    print("<label>")
    print("Final Year: ")
    print("</label>")
    print("<input id = 'fieldname' type='text' name='year2' >")
    print("<br>")
    print("<br>")
    print("<input type='submit' value='Submit' id='submit_button_year'>")
    print("<br>")
    print("</div>")
    print("<br>")

    print("</form>")
    print("<form action='' method ='POST'>")
    print("")
    print("<br>")
    print("Search Artist by ")
    print("<div class = 'custom-select' >")
    print("<select id = 'searchByArtist' name = 'searchByArtist' onchange='showSearchBar()'>")
    print("<option value = 'selectSomething'>-Select-</option>")
    print("<option value = 'Country'>Country</option>")
    print("<option value = 'BirthYear'>Birth Year</option>")
    print("</select>")
    print("</div>")
    print("<br>")
    print("<div class = 'type2' >")
    print("<label>")
    print("Search: ")
    print("</label>")
    print("<input id = 'fieldname' type='text' name='searchbar2' >")
    print("<input type='submit' value='Submit' id='submit_button'>")
    print("</div>")
    print("</form>")


chosenid = form.getvalue('searchbar') #userwill pick var
searchOption = form.getvalue('searchBy')
searchOptionArtist = form.getvalue('searchByArtist')

inputArtistSearch = form.getvalue('searchbar2')
inputYear1 = form.getvalue('year1')
inputYear2 = form.getvalue('year2')


def imagesThumbnails():
    idstring = "'" + str(chosenid) + "'"
    anothercursor5 = db.cursor()
    sql="SELECT image_id FROM detail WHERE type = " + idstring
    if (searchOption == 'Type'):
        sql="SELECT image_id FROM detail WHERE type = " + idstring
    if (searchOption == 'Location'):
        sql="SELECT image_id FROM detail WHERE location = " + idstring

    if (searchOption == 'ArtistName'):
        artistName = ''
        sql="SELECT artist_id FROM artist WHERE name = " + idstring
        anothercursor5.execute(sql)
        for imageid in anothercursor5.fetchall(): #cur.afetchone()
            artistName = imageid[0]
        aN = "'" + str(artistName) + "'"
        sql="SELECT image_id FROM image WHERE artist_id = " + aN
    if (searchOption == 'RangeofYear'):
        year1 = "'" + str(inputYear1) + "'"
        year2 = "'" + str(inputYear2) + "'"
        sql="SELECT image_id FROM detail WHERE year BETWEEN " + year1 + "AND" + year2


    anothercursor5.execute(sql)
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
            print("</div>")

def executeSQL():
    #searchOptionString = "'" + searchOption + "'"
    if(chosenid or (inputYear1 and inputYear2)):
        idstring = "'" + str(chosenid) + "'"
        foundMessage = ''

        anothercursor7 = db.cursor()
        sql="SELECT image_id FROM detail WHERE type = " + idstring
        foundMessage = "Images Found with Detail " +idstring +":"
        if (searchOption == 'Type'):
            sql="SELECT image_id FROM detail WHERE type = " + idstring
            foundMessage = "Images Found with Detail " +idstring +":"
        if (searchOption == 'Location'):
            sql="SELECT image_id FROM detail WHERE location = " + idstring
            foundMessage = "Images Found with Location " +idstring +":"
        if (searchOption == 'ArtistName'):
            artistName = ''
            sql="SELECT artist_id FROM artist WHERE name = " + idstring
            anothercursor7.execute(sql)
            for imageid in anothercursor7.fetchall(): #cur.afetchone()
                artistName = imageid[0]
            aN = "'" + str(artistName) + "'"
            sql="SELECT image_id FROM image WHERE artist_id = " + aN
            foundMessage = "Images Found by Artist " +idstring +":"
        if (searchOption == 'RangeofYear'):
            year1 = "'" + str(inputYear1) + "'"
            year2 = "'" + str(inputYear2) + "'"
            sql="SELECT image_id FROM detail WHERE year BETWEEN " + year1 + "AND" + year2
            foundMessage = "Images Found in The Year Range " +year1 + " to "+year2+":"

        anothercursor7.execute(sql)

        def firstImage():
            for imageid in anothercursor7.fetchall(): #cur.afetchone()
                return imageid[0]
        print(foundMessage)
        print("<br><br>")
        print("<div id = 'thumbnailwrapper'>")
        print("<div id = 'thumbnailssection' >")
        anImageId = firstImage()
        imagesThumbnails()
        srcstring = "/test/cgi-bin/imagedisplay.py?var=" + str(anImageId)
        print("</div>")
        print("</div>")
        print("<iframe id = 'bigpicdisplay' frameBorder = '0'  src = '",srcstring,"'>")
        print("</iframe>")

    if(inputArtistSearch):
        searchMsg = ''
        searchString = "'" + str(inputArtistSearch) + "'"
        anothercursor7 = db.cursor()
        sql="SELECT name FROM artist WHERE artist_id = " + searchString

        if (searchOptionArtist == 'Country'):
            sql="SELECT name FROM artist WHERE country = " + searchString
            searchMsg = "Artists From " + searchString + ":"
        if (searchOptionArtist == 'BirthYear'):
            sql="SELECT name FROM artist WHERE birth_year = " + searchString
            searchMsg = "Artists Born in " + searchString+":"
        anothercursor7.execute(sql)
        print(searchMsg)
        print("<br>")
        for artist in anothercursor7.fetchall(): #cur.afetchone()
            for y in range(len(artist)):
                print("Artist: ")
                print(artist[y])
                print("<br>")


print("Content-Type: text/html\r\n\r\n")
print()
print("<html>")
print("<head>")
print("<TITLE> Gallery </TITLE>")
print("</head>")
print("<body>")
print("<nav id = 'navi'>")
print("<a  href = '/test/cgi-bin/mp3.py'>Gallery</a>")
print("<a href = '/test/cgi-bin/creategallery.py'>  Create gallery</a>")
print("<a class = 'current' href = '/test/cgi-bin/search.py'> Search </a>")
print("</nav>")
print("<div id = 'creategallerywrapper'>")
print("<div id = 'creategallerydiv'>")
print("<p class = 'titlepage'>")
searchFunction()
print("</p>")
print("</div>")
print("</div>")
print("<div id = 'wrapper'>")
executeSQL()
print("</div>")

print("<script>")
print("function showSearchBar(){")
print("var x = document.getElementById('searchBy').value;")
print("var z = document.getElementById('searchByArtist').value;")

print("if (x == 'RangeofYear') {")
print("var y = document.getElementsByClassName('RangeofYear');")
print("y[0].style.visibility = 'visible';")
print("var y = document.getElementsByClassName('type');")
print("y[0].style.visibility = 'hidden';")
print("}")

print("if (x != 'RangeofYear') {")
print("var y = document.getElementsByClassName('type');")
print("y[0].style.visibility = 'visible';")
print("var y = document.getElementsByClassName('RangeofYear');")
print("y[0].style.visibility = 'hidden';")
print("}")

print("if(x == 'selectSomething'){")
print("var y = document.getElementsByClassName('RangeofYear');")
print("y[0].style.visibility = 'hidden';")
print("var y = document.getElementsByClassName('type');")
print("y[0].style.visibility = 'hidden';")
print("}")

print("if (z != 'selectSomething') {")
print("var p = document.getElementsByClassName('type2');")
print("p[0].style.visibility = 'visible';")
print("}")

print("if (z == 'selectSomething') {")
print("var p = document.getElementsByClassName('type2');")
print("p[0].style.visibility = 'hidden';")
print("}")

print("}")
print("function myFunction(param){")
print("var str = '/test/cgi-bin/imagedisplay.py?var=' + param.toString()")
print("document.getElementById('bigpicdisplay').src = str;")             #changing the image src
print("}")
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


print("#creategallerydiv{background-color: #393e46; margin: 0px; height: 320px;}")
print("#creategallerywrapper{}")
print(".titlepage{margin: 4px; padding: 0px;}")
print(".type{visibility: hidden; }")
print(".type2{visibility: hidden; }")
print(".RangeofYear{visibility: hidden; }")
print("h3{padding: 0px; margin: 0px; text-align: center;}")
print(".imgdiv{width: 150px; height: 150px; background-color: black; margin: auto; display: flex; justify-content: center; align-items: center;}")
print(".titleimagelink{background-color: #57A0D3; width: 310px; margin: 4px; height: 310px; float: left;}")
print("#wrapper{display: grid; grid-template-columns: 1fr 1fr;}")
print("#submit_button{ margin-left: 10px; padding-top: 1px; padding-bottom: 1px; width:auto; font-size: 16px; background-color: #00adb5; color: white; cursor: pointer; border: none;}")
print("label{display: inline-block; float: left; clear: left; text-align:left; width: 80px;}")
print("#fieldname{display: inline-block; float: left;}")
print("#submit_button_year{padding-top: 1px; padding-bottom: 1px; width: 16.2%; font-size: 16px; background-color: #00adb5; color: white; cursor: pointer; border: none;}")
print("</style>")



print("</html>")
db.close()
