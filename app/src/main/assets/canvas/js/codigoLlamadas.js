window.addEventListener("load", function(){
        var chart = new CanvasJS.Chart("chartContainer", {
		theme: "theme2",//theme1
		title:{
			text: "Numeros Populares"
		},
		animationEnabled: true,   // change to true
		data: [              
		{
			// Change type to "bar", "area", "spline", "pie",etc.
			type:  InterfazAndroid.tipo(),
			dataPoints: [
				{ label: InterfazAndroid.enviarTituloPopular(0),  y: InterfazAndroid.enviarRepetiocionesPopular(0)},
				{ label: InterfazAndroid.enviarTituloPopular(1), y: InterfazAndroid.enviarRepetiocionesPopular(1) },
				{ label: InterfazAndroid.enviarTituloPopular(2), y: InterfazAndroid.enviarRepetiocionesPopular(2) },
				{ label: InterfazAndroid.enviarTituloPopular(3),  y: InterfazAndroid.enviarRepetiocionesPopular(3) },
				{ label: InterfazAndroid.enviarTituloPopular(4),  y: InterfazAndroid.enviarRepetiocionesPopular(4) }
			]
		}
		]
	});
	chart.render();
});

