import React, { Component } from 'react';
import axios from 'axios';
import Button from 'components/MaterialUI/CustomButtons/Button.jsx';
import Grid from '@material-ui/core/Grid';
import CustomInput from 'components/MaterialUI/CustomInput/CustomInput.jsx';
import GridItem from 'components/MaterialUI/Grid/GridItem.jsx';

export default class MqttSettings extends Component {
	constructor(props) {
		super(props);

		this.state = {
			isLoading: false,
			error: null,
			settings: {
				topics: '',
				acceptedMeasurements: '',
				ignoredMeasurements: '',
				brokerAddress: '',
				brokerUsername: '',
				brokerPassword: ''
			}
		};

		this.createMqttConnection = this.createMqttConnection.bind(this);
		this.deleteSettings = this.deleteSettings.bind(this);
		this.handleChangeOnObject = this.handleChangeOnObject.bind(this);
	}

	componentDidMount() {
		this.setState({ isLoading: true });
		axios
			.get('http://localhost:8090/settings/all')
			.then(result =>
				this.setState({
					settings: result.data,
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

	deleteSettings() {
		axios
			.post('http://localhost:8090/settings/delete', {})
			.then(
				result =>
					this.setState({
						settings: result.data,
						isLoading: false
					}),
				alert('settings deleted')
			)
			.catch(function(error) {
				console.log(error);
			});
	}

	handleChangeOnObject(event) {
		this.setState({
			settings: {
				...this.state.settings,
				[event.target.id]: event.target.value
			}
		});
	}

	createMqttConnection() {
		let settings = { ...this.state.settings };
		if (typeof settings.acceptedMeasurements === 'string')
			settings.acceptedMeasurements = this.state.settings.acceptedMeasurements.split(
				','
			);
		if (typeof settings.ignoredMeasurements === 'string')
			settings.ignoredMeasurements = this.state.settings.ignoredMeasurements.split(
				','
			);
		if (typeof settings.topics === 'string')
			settings.topics = this.state.settings.topics.split(',');

		this.setState({ settings });

		axios
			.post('http://localhost:8090/settings', settings)
			.then(
				result =>
					this.setState({
						settings: result.data,
						isLoading: false
					}),
				alert('settings updated')
			)
			.catch(function(error) {
				console.log(error);
			});
	}

	render() {
		return (
			<div>
				<h1>Connect to MQTT Broker</h1>
				<h3>Broker settings</h3>
				<Grid container>
					<GridItem xs={12} sm={12} md={4}>
						tcp://139.6.17.21:1883
						<CustomInput
							labelText="Broker Address"
							id="brokerAddress"
							formControlProps={{
								fullWidth: true
							}}
							inputProps={{
								value: this.state.settings.brokerAddress,
								onChange: this.handleChangeOnObject
							}}
						/>
					</GridItem>
					<GridItem xs={12} sm={12} md={4}>
						absolvent
						<CustomInput
							labelText="Broker Username"
							id="brokerUsername"
							inputProps={{
								value: this.state.settings.brokerUsername,
								onChange: this.handleChangeOnObject
							}}
							formControlProps={{
								fullWidth: true
							}}
						/>
					</GridItem>
					<GridItem xs={12} sm={12} md={4}>
						THKAbsolvent17
						<CustomInput
							labelText="Broker Password"
							id="brokerPassword"
							inputProps={{
								value: this.state.settings.brokerPassword,
								onChange: this.handleChangeOnObject
							}}
							formControlProps={{
								fullWidth: true
							}}
						/>
					</GridItem>
				</Grid>

				<h3>MQTT Client settings</h3>
				<Grid container>
					<GridItem xs={12} sm={12} md={12}>
						<CustomInput
							labelText="Accepted Measurements"
							id="acceptedMeasurements"
							formControlProps={{
								fullWidth: true
							}}
							inputProps={{
								multiline: true,
								value: this.state.settings.acceptedMeasurements,
								onChange: this.handleChangeOnObject
							}}
						/>
					</GridItem>
					<GridItem xs={12} sm={12} md={12}>
						<CustomInput
							labelText="Ignored Measurements"
							id="ignoredMeasurements"
							inputProps={{
								multiline: true,
								value: this.state.settings.ignoredMeasurements,
								onChange: this.handleChangeOnObject
							}}
							formControlProps={{
								fullWidth: true
							}}
						/>
					</GridItem>
					<GridItem xs={12} sm={12} md={12}>
						<CustomInput
							labelText="Topics"
							id="topics"
							inputProps={{
								multiline: true,
								value: this.state.settings.topics,
								onChange: this.handleChangeOnObject
							}}
							formControlProps={{
								fullWidth: true
							}}
						/>
					</GridItem>
				</Grid>

				<Button
					type="button"
					color="primary"
					onClick={this.createMqttConnection}
				>
					Update Settings
				</Button>
				<Button type="button" color="danger" onClick={this.deleteSettings}>
					Delete Settings
				</Button>
			</div>
		);
	}
}
