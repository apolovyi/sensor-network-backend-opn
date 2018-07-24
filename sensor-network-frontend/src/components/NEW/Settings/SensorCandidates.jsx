import React, { Component } from 'react';
import axios from 'axios';
import Sensor from './Sensor';
import ExistingSensor from './ExistingSensor';
import Button from 'components/CustomButtons/Button.jsx';

export default class SensorCandidates extends Component {
	constructor(props) {
		super(props);

		this.state = {
			temporarySensors: [],
			existingSensors: [],
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
			.get('http://localhost:8090/sensors')
			.then(result =>
				this.setState({
					existingSensors: result.data,
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

		var existingSensors = this.state.existingSensors.map(sensor => (
			<ExistingSensor
				sensorProducts={this.state.sensorProducts}
				sensorRooms={this.state.sensorRooms}
				key={sensor.sensorID}
				name={sensor.sensorName}
				room={sensor.room}
				sensorProduct={sensor.room}
				measurements={sensor.measurements}
				sensor={sensor}
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
				<h1>Sensor candidates</h1>
				{tempSensors}
				<br />
				<h1>Existing sensors</h1>
				{existingSensors}
			</div>
		);
	}
}
