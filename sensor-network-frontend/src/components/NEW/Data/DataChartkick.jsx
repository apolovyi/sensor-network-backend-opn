import ReactChartkick, { LineChart, PieChart } from 'react-chartkick';
import Chart from 'chart.js';
import React from 'react';

export default class DataChartkick extends React.Component {
	constructor(props) {
		super(props);
		this.state = { measurements: [] };
	}

	render() {
		var newObj = Object.assign({}, ...this.props.data);
		console.log(newObj);

		var result = {};
		var arr = this.props.data;
		for (var i = 0; i < arr.length; i++) {
			//result[Math.round(arr[i].ts * 1000)] = arr[i].value;
			var date = new Date(arr[i].ts);
			console.log(date);
			result[date] = arr[i].value;
		}
		console.log(result);
		//var newData = this.props.measurements.map(x => x.timestamp);
		if (result.length !== 0) {
			return <LineChart data={result} />;
		}
		return null;
	}
}
DataChartkick.defaultProps = {
	measurements: []
};
