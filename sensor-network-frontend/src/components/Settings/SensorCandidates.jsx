import React, { Component } from 'react';
import axios from 'axios';
import SensorCandidate from './SensorCandidate';
import ExistingSensor from './ExistingSensor';

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
			<SensorCandidate
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

		if (this.state.error) {
			return <div>Can't connect to database</div>;
		}

		return (
			<div>
				<h1>Existing sensors</h1>
				{existingSensors.length !== 0
					? existingSensors
					: 'No existing Sensors in database'}
				<br />
				<h1>Sensor candidates</h1>
				{tempSensors.length !== 0
					? tempSensors
					: 'No sensor candidates in database'}
				<br />
			</div>
		);
	}
}
