<?php 
// process calendar data in format
// jahr,monat,tag,,blau,blau,gelb,gelb,bio,bio,sack,kleider
// 0    1     2  3 4    5    6    7    8   9   10    11
// 2016,1,2,Sa,,,4,6,,16,14,15
//
// Part 1 - create .csv with format:
// grged,5,2016-02-25
//
// Part 2 - create json struct, including streetnames from streets.csv
// format:
/*

{
  "calendars" : [ {
    "city" : "Bünde",
    "country" : "Germany",
    "districts" : [ {
      "dates" : [ {
        "date" : "2015-11-30",
        "type" : "GB"
      }, {
        "date" : "2015-12-03",
        "type" : "BIO"
      } ],
      "name" : "Bezirk 1",
      "sections" : [ {
        "comment" : "Boschstr. bis Rödinghauser Str.",
        "street" : "Ahler Straße"
      }, {
        "street" : "Am Flachhof"
      }, {
        "street" : "Am Schnellweg"
      } ]
    }, {
      "dates" : [ {
      ...

*/
// KEK 12/2015


$inputfile = "Kalender-2016.csv";
$streetfile = "streets.csv";

$outputfile = "Kalender-2016-processed.csv";
$outputfile_json = "Kalender-2016-processed.json";

// read data input
$lines = file($inputfile);

function adddate($date, $region, $type) {
  global $jsondates;
  if (empty($jsondates[$region])) {
    $jsondates[$region] = array ();
  }
  $jsondates[$region][] = array (
    "date" => substr($date,1), 
    "type" => $type
  );
}

$outarray = array();

$jsondates = array();

// process entries 
foreach ($lines as $line_num => $line) {
  if ($line_num > 0) { // skip first line
    $data = explode(",",$line);
    $datum = ",".sprintf("%04d-%02d-%02d", $data[0], $data[1], $data[2]);
    if (!empty($data[4])) { $outarray[] = "grbld".",".$data[4].$datum; adddate($datum, $data[4], "GB"); }
    if (!empty($data[5])) { $outarray[] = "grbld".",".$data[5].$datum; adddate($datum, $data[5], "GB"); }
    if (!empty($data[6])) { $outarray[] = "grged".",".$data[6].$datum; adddate($datum, $data[6], "GG"); }
    if (!empty($data[7])) { $outarray[] = "grged".",".$data[7].$datum; adddate($datum, $data[7], "GG"); }
    if (!empty($data[8])) { $outarray[] = "bio".",".$data[8].$datum; adddate($datum, $data[8], "BIO"); }
    if (!empty($data[9])) { $outarray[] = "bio".",".$data[9].$datum; adddate($datum, $data[9], "BIO"); }
    if (!empty($data[10])) { $outarray[] = "gs".",".$data[10].$datum; adddate($datum, $data[10], "GS"); }
    // there is a cr at the end of the line, special treatment:
    if (intval($data[11]) > 0) {
      $outarray[] = "ak".",".intval($data[11]).$datum;
      adddate($datum, $data[11], "AK");
	}
    
  }
}

// out to terminal
echo implode("\n",$outarray);

// output to file
$fout = fopen($outputfile,"w");
fwrite ($fout, implode("\n",$outarray));
fclose($fout);

echo "\nend\n Written: ".$outputfile."\n";

// Part 2: json

$arrtrashtypes = array(
		"GB" => array (
		    "shortname" => "GB",
			"fullname" => "Restmüll (grauer und blauer Deckel)",
			"icon" => "ic_bins_grged",
			"color" => ""
		),
		"GG" => array(
		    "shortname" => "GG",
			"fullname" => "Restmüll (grauer und blauer Deckel)",
			"icon" => "ic_bins_grbld",
			"color" => ""
		),
		"BIO" => array(
		    "shortname" => "BIO",
			"fullname" => "Biomüll und Altpapier",
			"icon" => "ic_bins_bio",
			"color" => ""
		),
		"AK" => array(
  		    "shortname" => "AK",
			"fullname" => "Altkleidersammlung",
			"icon" => "ic_bins_ak",
			"color" => ""
		),
	    "GS" => array(
	        "shortname" => "GS",
			"fullname" => "Gelber Sack / Grüne Tonne",
			"icon" => "ic_bins_gs",
			"color" => ""
		)
);

$arrregions = array();

function addstreet($streetname, $region, $comment) {
	global $arrregions;
	if (empty($arrregions[$region])) {
     $arrregions[$region] = array ();
    }
    $comment = str_replace("\n","",$comment);
    if (strlen($comment) > 1) {
    	$arrregions[$region][] = array ("street" => $streetname, "comment" => $comment);
    } else {
    	$arrregions[$region][] = array ("street" => $streetname);
    }
   
}

$streets = file($streetfile);

foreach ($streets as $street_num => $street) {
	$streetdata = explode(",", $street);
	addstreet($streetdata[0],$streetdata[1],$streetdata[2]);
}

$arrdistricts = array();
$arrdistrictdates = array();

foreach ($arrregions as $region_no => $region_data) {
	if (!empty($jsondates[$region_no])) {
		$arrdistricts[$region_no] = array (
		    "name" => "Bezirk ".$region_no,
			"sections" =>  $region_data
		);
		$arrdistrictdates[$region_no] = $jsondates[$region_no];		
	}
}

$arrjson = array("calendars" => array(
	array ("city" => "Bünde", 
	"country" => "Germany",
	"postcode" => "32257",
	"latitude" => 52.2101791,
	"longitude" => 8.4854144,
	"trashtypes" => $arrtrashtypes,
	"districts" => $arrdistricts,
	"districtdates" => $arrdistrictdates,
	)
));

// out to terminal
echo json_encode($arrjson);

// output to file
$fout = fopen($outputfile_json,"w");
fwrite ($fout, json_encode($arrjson));
fclose($fout);

echo "\nend\n Written: ".$outputfile_json."\n";