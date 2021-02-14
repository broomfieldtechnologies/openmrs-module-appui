<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {font-family: Arial, Helvetica, sans-serif;}
* {box-sizing: border-box;}

input[type=text], select, textarea {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  margin-top: 6px;
  margin-bottom: 16px;
  resize: vertical;
}

input[type=submit] {
  background-color: #4CAF50;
  color: white;
  padding: 12px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

input[type=submit]:hover {
  background-color: #45a049;
}

.container {
  border-radius: 5px;
  background-color: #f2f2f2;
  padding: 20px;
}
.errors {
  color: red;
  margin-bottom: 10px;
}
</style>
</head>
<body>

<h3>Contact us</h3>

<div class="container">

	<div class="errors">
		<% errors.each{ error  -> %>
		<li>
			${error}
		</li>
		<% } %>
	</div>
    
  * Required fields.
    
  <p></p>
  
  <form method="post" action="/openmrs/appui/help.page" enctype="multipart/form-data">
  
    <label for="email">* Your email</label>
    <input type="text" id="email" name="email" placeholder="Your email.." value="${email}">

    <label for="subject">* Subject</label>
    <input type="text" id="subject" name="subject" placeholder="Subject.." value="${subject}">

    <label for="message">* Message</label>
    <textarea id="message" name="message" placeholder="Your message.." style="height:200px">${message}</textarea>
    
    <label for="files">Files</label>
    <input type="file" id="files" name="files" multiple>

    <input type="submit" value="Submit">
  </form>
</div>

</body>
</html>
