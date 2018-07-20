import React, { Component } from 'react';
import Button from 'components/CustomButtons/Button.jsx';
import Grid from '@material-ui/core/Grid';
import GridItem from 'components/Grid/GridItem.jsx';
import axios from 'axios';
export default class Measurements extends Component {
	constructor(props) {
		super(props);
		this.state = { isLoading: false };
		this.removeMeasurement = this.removeMeasurement.bind(this);
	}

	removeMeasurement(event) {
		this.setState({ isLoading: true });
		axios
			.post(
				'http://localhost:8090/settings/ignoredMeasurements',
				event.currentTarget.id,
				{
					headers: {
						'Content-Type': 'text/plain'
					}
				}
			)
			.then(result =>
				this.setState({
					isLoading: false
				})
			)
			.catch(function(error) {
				console.log(error);
			});
	}

	render() {
		var measurements = this.props.measurements.map((measurements, index) => (
			<Grid container>
				<GridItem xs={12} sm={4} md={4}>
					<span className="measurment-name">{measurements.measurement}</span>
				</GridItem>
				<GridItem xs={12} sm={6} md={6}>
					<span className="measurment-value">{measurements.values}</span>
				</GridItem>
				<GridItem xs={12} sm={2} md={2}>
					<Button
						type="button"
						justIcon
						round
						color="danger"
						id={measurements.measurement}
						name={measurements.measurement}
						onClick={this.removeMeasurement}
					>
						<i class="material-icons">clear</i>
					</Button>
				</GridItem>
			</Grid>
		));
		return <div className="measurements">{measurements}</div>;

		//<ul>{this.props.topics.map(topic => <li>{topic.name}</li>)}</ul>;
	}
}
