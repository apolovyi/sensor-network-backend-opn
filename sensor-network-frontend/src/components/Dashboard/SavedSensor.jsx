import React, { Component } from 'react';
import axios from 'axios';
import DataChart from './DataChart';
import { withStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import GridItem from 'components/MaterialUI/Grid/GridItem.jsx';
import Button from 'components/MaterialUI/CustomButtons/Button.jsx';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';

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

export default withStyles(styles)(
	class SavedSensor extends Component {
		constructor(props) {
			super(props);
			this.state = {
				measurements: [],
				unit: '',
				data: {},
				timePeriod: 'all'
			};
			this.handleChange = this.handleChange.bind(this);
			this.showData = this.showData.bind(this);
		}

		handleChange(event) {
			this.setState({ [event.target.id]: event.target.value });
		}

		showData(event) {
			this.setState({ isLoading: true });
			var mID = [this.props.sensorID] + '_' + [event.currentTarget.id];
			axios
				.get('http://localhost:8090/measurements/' + mID, {
					params: {
						timePeriod: this.state.timePeriod
					}
				})
				.then(result =>
					this.setState({
						data: result.data.measurementPairs,
						unit: result.data.unit,
						isLoading: false
					})
				)
				.catch(function(error) {
					console.log(error);
				});
		}

		render() {
			var measurements = this.props.measurements.map(measurement => (
				<div key={measurement}>
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
				<div className="sensor" key={this.props.sensorID}>
					<h5 className="sensor-id">{this.props.sensorName}</h5>
					<Grid container>
						<GridItem xs={12} sm={12} md={12} />
					</Grid>
					{measurements}
					<GridItem xs={12} sm={12} md={4}>
						<div className="root">
							<FormControl className="formControl">
								<InputLabel htmlFor="timePeriod">Time period</InputLabel>
								<Select
									native
									value={this.state.timePeriod}
									onChange={this.handleChange}
									inputProps={{
										name: 'timePeriod',
										id: 'timePeriod'
									}}
								>
									<option value="all" />
									<option value="day">Last day</option>
									<option value="week">Last week</option>
									<option value="month">Last month</option>
								</Select>
							</FormControl>
						</div>
					</GridItem>
					<br />
					<GridItem xs={12} sm={12} md={12}>
						<DataChart
							data={this.state.data}
							timePeriod={this.state.timePeriod}
							unit={this.state.unit}
						/>
					</GridItem>
					<br />
				</div>
			);
		}
	}
);
