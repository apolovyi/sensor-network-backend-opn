import React, { Component } from 'react';
import Measurements from './Measurements';
import axios from 'axios';
import DataChartkick from './DataChartkick';

import { withStyles } from '@material-ui/core/styles';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';

import Grid from '@material-ui/core/Grid';
import CustomInput from 'components/CustomInput/CustomInput.jsx';
import GridItem from 'components/Grid/GridItem.jsx';
import Button from 'components/CustomButtons/Button.jsx';

const styles = theme => ({
	root: {
		display: 'flex',
		flexWrap: 'wrap'
	},
	formControl: {
		margin: theme.spacing.unit,
		minWidth: 120
	},
	selectEmpty: {
		marginTop: theme.spacing.unit * 2
	}
});

class SavedSensor extends Component {
	constructor(props) {
		super(props);
		this.state = {
			measurements: [],
			data: {}
		};
		this.handleChange = this.handleChange.bind(this);
		this.showData = this.showData.bind(this);
	}

	handleChange(event) {
		this.setState({ [event.target.id]: event.target.value });
	}

	showData(event) {
		this.setState({ isLoading: true });
		var mID =
			[this.props.sensorID] + '_' + [event.currentTarget.id] + '_2018_7';
		axios
			.get('http://localhost:8090/measurements/' + mID, {})
			.then(result =>
				this.setState({
					data: result.data.measurementPairs,
					isLoading: false
				})
			)
			.catch(function(error) {
				console.log(error);
			});

		//this.state.data = {};
		//this.state.measurements.map;
	}

	/*  <Measurements measurements={this.props.measurements} /> */

	render() {
		var measurements = this.props.measurements.map(measurement => (
			<div>
				<Grid container>
					<GridItem xs={12} sm={12} md={4}>
						{measurement}
					</GridItem>
					<GridItem xs={12} sm={12} md={6}>
						<Button
							id={measurement}
							type="button"
							color="success"
							round
							onClick={this.showData}
						>
							Show Data
						</Button>
					</GridItem>
				</Grid>
			</div>
		));

		return (
			<div className="sensor">
				<h5 className="sensor-id">{this.props.sensorName}</h5>
				<Grid container>
					<GridItem xs={12} sm={12} md={12} />
				</Grid>
				{measurements}

				<br />
				<GridItem xs={12} sm={12} md={12}>
					<DataChartkick data={this.state.data} />
				</GridItem>
				<br />
			</div>
		);

		//<ul>{this.props.topics.map(topic => <li>{topic.name}</li>)}</ul>;
	}
}

export default withStyles(styles)(SavedSensor);
