import React, { Component } from 'react';
import axios from 'axios';
import SavedSensor from './SavedSensor';
import Button from 'components/CustomButtons/Button.jsx';

export default class SavedSensors extends Component {
	constructor(props) {
		super(props);

		this.state = {
			savedSensors: [],
			isLoading: false,
			error: null
			//sensorProducts: [],
			//sensorRooms: []
		};
		//this.createSensors = this.createSensors.bind(this);
		//this.addSensorFromTd = this.addSensorFromTd.bind(this);
	}

	componentDidMount() {
		this.setState({ isLoading: true });
		axios
			.get('http://localhost:8090/sensors')
			.then(result =>
				this.setState({
					savedSensors: result.data,
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

	/* createSensors() {
		this.setState({ isLoading: true });
		axios
			.post('http://localhost:8090/settings/addAll', {})
			.then(result =>
				this.setState({
					savedSensors: result.data.savedSensors,
					isLoading: false
				})
			)
			.catch(function(error) {
				console.log(error);
			});
	} */

	render() {
		var savedSensor = this.state.savedSensors.map(sensor => (
			<SavedSensor
				key={sensor._id}
				sensorID={sensor._id}
				measurements={sensor.measurements}
				sensorName={sensor.sensorName}
				tempSensor={sensor}
			/>
		));
		if (savedSensor.length === 0) {
			return 'Sensor database is empty';
		}
		if (this.state.error) {
			return <div>Can't connect to database</div>;
		}

		return (
			<div>
				<h1>Existing Data</h1>
				{savedSensor}
			</div>
		);
	}
}
