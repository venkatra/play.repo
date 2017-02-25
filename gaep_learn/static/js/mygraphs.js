queue()
    .defer(d3.csv, "/static/data.csv")
    .await(makeGraphs);
//.defer(d3.json, "/donorschoose/projects")
//    .defer(d3.json, "static/geojson/us-states.json")

function makeGraphs(error, csvdata) {


	//Clean projectsJson data
	//var donorschooseProjects = projectsJson;
	var dateFormat = d3.time.format("%m/%d/%Y %H:%M:%S %p");//10/08/2013 07:30:00 PM

	var ex_data="10/30/2013 05:35:58 PM";
	console.log("Parse ["+ex_data+ "] :" + dateFormat.parse(ex_data));

	csvdata.forEach(function(d) {
		d["Created Date"] = dateFormat.parse(d["Created Date"]);
		d["Closed Date"] = dateFormat.parse(d["Closed Date"]);
		d["cnt"] = 1
       // console.log(d);
	});

//console.log(csvdata);

	//Create a Crossfilter instance
	var ndx = crossfilter(csvdata);

	//Define Dimensions
	var createdDateDim = ndx.dimension(function(d) { return d["Created Date"]; });
	var AgencyDim = ndx.dimension(function(d) { return d["Agency"]; });
	var ComplaintTypeDim = ndx.dimension(function(d) { return d["Complaint Type"]; });
//	var LocationTypeDim = ndx.dimension(function(d) { return d["Location Type"]; });
	var CityDim  = ndx.dimension(function(d) { return d["Address Type"]; });

	//Calculate metrics
	var numcreatedDateDim = createdDateDim.group();
	var numAgencyDim = AgencyDim.group();
	var numComplaintTypeDim = ComplaintTypeDim.group();
	var numCityDim = CityDim.group();
	var totalCNT = CityDim.group().reduceSum(function(d) {
		return d["cnt"];
	});

	var all = ndx.groupAll();

	var totalGCounts = ndx.groupAll().reduceSum(function(d) {return d["cnt"];});
//	var max_state = totalDonationsByState.top(1)[0].value;

	//Define values (to be used in charts)
	var minDate = createdDateDim.bottom(1)[0]["Created Date"];
	var maxDate = createdDateDim.top(1)[0]["Created Date"];
//    console.log("Date range : " + minDate + "to" + maxDate)

    //Charts
	var timeChart = dc.barChart("#time-chart");
	var resourceTypeChart = dc.rowChart("#resource-type-row-chart");
	var povertyLevelChart = dc.rowChart("#poverty-level-row-chart");
//	var usChart = dc.geoChoroplethChart("#us-chart");
	var numberProjectsND = dc.numberDisplay("#number-projects-nd");
	var totalDonationsND = dc.numberDisplay("#total-donations-nd");

	numberProjectsND
		.formatNumber(d3.format("d"))
		.valueAccessor(function(d){return d; })
		.group(all);

	totalDonationsND
		.formatNumber(d3.format("d"))
		.valueAccessor(function(d){return d; })
		.group(totalGCounts)
		.formatNumber(d3.format(".3s"));

	timeChart
		.width(600)
		.height(160)
		.margins({top: 10, right: 50, bottom: 30, left: 50})
		.dimension(createdDateDim)
		.group(numcreatedDateDim)
		.transitionDuration(1)
		.x(d3.time.scale().domain([minDate, maxDate]))
		.elasticY(true)
		.xAxisLabel("Year")
		.yAxis().ticks(4);

	resourceTypeChart
        .width(300)
        .height(550)
        .dimension(AgencyDim)
        .group(numAgencyDim)
        .xAxis().ticks(4);

	povertyLevelChart
		.width(300)
		.height(550)
        .dimension(CityDim)
        .group(numCityDim)
        .xAxis().ticks(4);

//
//	usChart.width(1000)
//		.height(330)
//		.dimension(stateDim)
//		.group(totalDonationsByState)
//		.colors(["#E2F2FF", "#C4E4FF", "#9ED2FF", "#81C5FF", "#6BBAFF", "#51AEFF", "#36A2FF", "#1E96FF", "#0089FF", "#0061B5"])
//		.colorDomain([0, max_state])
//		.overlayGeoJson(statesJson["features"], "state", function (d) {
//			return d.properties.name;
//		})
//		.projection(d3.geo.albersUsa()
//    				.scale(600)
//    				.translate([340, 150]))
//		.title(function (p) {
//			return "State: " + p["key"]
//					+ "\n"
//					+ "Total Donations: " + Math.round(p["value"]) + " $";
//		})

    dc.renderAll();

};