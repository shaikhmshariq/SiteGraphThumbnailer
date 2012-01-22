<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
  <head>
  <script type="text/javascript">
  <![CDATA[
  function toggleElement(elem){
    var tableElem = elem.parentNode.parentNode;
	var allRows = tableElem.getElementsByTagName("TR");
	for(var i=1;i < allRows.length; i++){
		var row = allRows[i];
		if(row.style.display == 'none')
			row.style.display = '';
		else
			row.style.display = 'none';
		}
  }]]>
  </script>
  <link href="./apis.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
  <xsl:variable name="imagesURI">http://HOST/SiteGraphThumbnailer/images</xsl:variable>
 <table width="95%" align="center" border="0">
 <tr>
	<th colspan="2"><h2>SiteGraphThumbnailer API List</h2></th>
 </tr>
 <tr>
	<td colspan="2"><br/></td>
 </tr>
 <xsl:for-each select="apis/api">
 <tr>
	<td width="5%" valign="top" style="padding-top: 10px;"><xsl:number />.</td>
	<td>
	<table border="0" width="100%" align="center" cellspacing="5" cellpadding="5">
	  <tr onClick="toggleElement(this)" style="cursor: pointer;">
		<td width="18%" class="header">URL:</td>
		<td >
			<xsl:value-of select="uri/@method"/><span class="link"><xsl:copy-of select="$imagesURI" />/<xsl:value-of select="uri"/>/</span>
		</td>
	  </tr>
	  <xsl:if test="pathvariables/pathvariable">
	  <tr style="display:none;">
		<td class="header">Path Variables:</td>
		<td>
		<table width="80%" cellpadding="5" cellspacing="0" border="1">
		<xsl:for-each select="pathvariables/pathvariable">
			<tr style="display:none;">
				<td width="5%" style="text-align:center;"><xsl:number /></td>
				<td width="30%"><xsl:value-of select="name" /> (<xsl:value-of select="./@type" />)</td>
				<td>
					<div class="desc"><xsl:value-of select="description" /><br/>
					<span class="example">E.g. <xsl:value-of select="possiblevalues" /></span>
					</div>
				</td>
			</tr>
		</xsl:for-each>
		</table>
		</td>
	  </tr>
	  </xsl:if>
	  <tr style="display:none;">
		<td class="header">Parameters:</td>
		<td>
		<table width="80%"  cellspacing="0" cellpadding="5" border="1">
		<xsl:for-each select="params/param">
			<tr style="display:none;">
				<td width="5%" style="text-align:center;"><xsl:number /></td>
				<td width="30%"><xsl:value-of select="name" /> (<xsl:value-of select="./@type" />) <xsl:if test="./@mandatory = 'true'"><span class="mandatory">*</span></xsl:if></td>
				<td><div class="desc"><xsl:value-of select="description" /></div></td>
			</tr>
		</xsl:for-each>
		</table>
		</td>
	  </tr>
	  <tr style="display:none;">
		<td class="header">Example Request:</td><td><xsl:value-of select="uri/@method"/>  
		<span class="link example"><xsl:copy-of select="$imagesURI" />/<xsl:value-of select="example"/></span></td>
	  </tr>
	  <tr style="display:none;">
		<td class="header">Description:</td><td><xsl:value-of select="description"/></td>
	  </tr>
	</table>
	</td>
	</tr>
	<tr>
		<td colspan="2"><hr/></td>
	</tr>
  </xsl:for-each>
  </table>
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>

