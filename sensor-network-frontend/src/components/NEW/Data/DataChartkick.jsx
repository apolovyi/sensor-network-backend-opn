import ReactChartkick, { LineChart, PieChart } from 'react-chartkick';
import Chart from 'chart.js';
import React from 'react';
ReactChartkick.addAdapter(Chart);

export default class DataChartkick extends React.Component {
	constructor(props) {
		super(props);
		/* 		this.state = {
			measurements: []
		}; */
	}

	render() {
		var newObj = Object.assign({}, ...this.props.data);
		console.log(newObj);

		var data = {};
		var arr = this.props.data;
		for (var i = 0; i < arr.length; i++) {
			var date = new Date(arr[i].ts);
			data[date] = arr[i].value;
		}
		console.log(data);

		return data.length !== 0 ? (
			<LineChart
				messages={{ empty: 'Click show data' }}
				data={data}
				xtitle="Time"
				ytitle={this.props.unit}
				download={true}
			/>
		) : (
			'Nothing to show'
		);
	}
}
