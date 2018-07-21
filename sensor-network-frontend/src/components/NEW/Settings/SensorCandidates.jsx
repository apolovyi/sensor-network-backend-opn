import React, { Component } from 'react';
import axios from 'axios';
import Sensor from './Sensor';
import Button from 'components/CustomButtons/Button.jsx';

export default class SensorCandidates extends Component {
	constructor(props) {
		super(props);

		this.state = {
			temporarySensors: [],
			isLoading: false,
			error: null,
			sensorProducts: [],
			sensorRooms: []
		};
		//this.createSensors = this.createSensors.bind(this);
		//this.addSensorFromTd = this.addSensorFromTd.bind(this);
	}

	componentDidMount() {
		this.setState({ isLoading: true });
		axios
			.get('http://localhost:8090/settings/sensors')
			.then(result =>
				this.setState({
					temporarySensors: result.data.temporarySensors,
					isLoading: false
				})
			)
			.catch(error =>
				this.setState({
					error,
					isLoading: false
				})
			);

		this.setState({ isLoading: true });
		axios
			.get('http://localhost:8090/settings/sensorProducts')
			.then(result =>
				this.setState({
					sensorProducts: result.data,
					isLoading: false
				})
			)
			.catch(error =>
				this.setState({
					error,
					isLoading: false
				})
			);

		this.setState({ isLoading: true });
		axios
			.get('http://localhost:8090/settings/rooms')
			.then(result =>
				this.setState({
					sensorRooms: result.data,
					isLoading: false
				})
			)
			.catch(error =>
				this.setState({
					error,
					isLoading: false
				})
			);
	}

	/* 	createSensors() {
		this.setState({ isLoading: true });
		axios
			.post('http://localhost:8090/settings/addAll', {})
			.then(result =>
				this.setState({
					temporarySensors: result.data.temporarySensors,
					isLoading: false
				})
			)
			.catch(function(error) {
				console.log(error);
			});
	} */

	render() {
		var tempSensors = this.state.temporarySensors.map(sensor => (
			<Sensor
				sensorProducts={this.state.sensorProducts}
				sensorRooms={this.state.sensorRooms}
				key={sensor.sensorID}
				sensorID={sensor.sensorID}
				measurements={sensor.measurements}
				tempSensor={sensor}
			/>
		));
		if (tempSensors.length === 0) {
			return 'No sensors candidates. Please check dashboard.';
		}
		if (this.state.error) {
			return <div>Can't connect to database</div>;
		}

		return (
			<div>
				<h1>Sensor Candidates</h1>
				{tempSensors}
				{/* <Button type="button" color="primary" onClick={this.createSensors}>
					Add all
				</Button> */}
			</div>
		);
	}
}
