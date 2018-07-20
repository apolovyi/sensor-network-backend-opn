import React, { Component } from 'react';
import axios from 'axios';
import Button from 'components/CustomButtons/Button.jsx';

import Grid from '@material-ui/core/Grid';
import CustomInput from 'components/CustomInput/CustomInput.jsx';
import GridItem from 'components/Grid/GridItem.jsx';

export default class MqttSettings extends Component {
	constructor(props) {
		super(props);

		this.state = {
			//settings: { brokerAddress: '', brokerUsername: '', brokerPassword: '' },
			isLoading: false,
			error: null,
			settings: {
				topics: '',
				acceptedMeasurements: '',
				ignoredMeasurements: ''
			},
			brokerAddress: '',
			brokerUsername: '',
			brokerPassword: ''
			//topics: '',
			//ignoredMeasurements: '',
			//acceptedMeasuremnts: ''
		};

		this.createMqttConnection = this.createMqttConnection.bind(this);
		this.createMqttConnection2 = this.createMqttConnection2.bind(this);
		this.handleChange = this.handleChange.bind(this);
		this.handleChangeOnObject = this.handleChangeOnObject.bind(this);
	}

	componentDidMount() {
		var ignoredMeasurements =
			'FAULT_REPORTING,ERROR,LOWBAT,LED_STATUS,UNREACH,STICKY_UNREACH,CONTROL_MODE,COMMUNICATION_REPORTING,PARTY_STOP_MONTH,PARTY_START_MONTH,PARTY_STOP_DAY,PARTY_STOP_TIME,PARTY_STOP_YEAR,WINDOW_OPEN_REPORTING,LOWBAT_REPORTING,PARTY_START_YEAR,PARTY_START_TIME,PARTY_START_DAY,CONFIG_PENDING';
		var topics = 'th/hm/status/';

		this.setState({
			brokerAddress: 'tcp://139.6.17.21:1883',
			brokerUsername: 'absolvent',
			brokerPassword: 'THKAbsolvent17'
			//acceptedMeasuremnts: ''
			//[settings.ignoredMeasurements]: ignoredMeasurements,
			//[settings.topics]: topics
		});

		this.setState({
			settings: {
				...this.state.settings
				//topics: topics,
				//acceptedMeasurements: ignoredMeasurements,
				//ignoredMeasurements: ignoredMeasurements
			}
		});

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

	handleChange(event) {
		this.setState({ [event.target.id]: event.target.value });
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
		axios
			.post('http://localhost:8090/settings/start', {
				brokerAddress: this.state.brokerAddress,
				brokerUsername: this.state.brokerUsername,
				brokerPassword: this.state.brokerPassword,
				ignoredMeasurements: this.state.settings.ignoredMeasurements,
				acceptedMeasurements: this.state.settings.acceptedMeasurements,
				topics: this.state.settings.topics
			})
			.then(result =>
				this.setState({
					settings: result.data,
					isLoading: false
				})
			)
			.catch(function(error) {
				console.log(error);
			});

		/* this.setState(prevState => ({
			isToggleOn: !prevState.isToggleOn
		})); */
	}

	createMqttConnection2() {
		axios
			.post('http://localhost:8090/settings/connect', {
				brokerAddress: this.state.brokerAddress,
				brokerUsername: this.state.brokerUsername,
				brokerPassword: this.state.brokerPassword,
				ignoredMeasurements: this.state.settings.ignoredMeasurements,
				acceptedMeasurements: this.state.settings.acceptedMeasurements,
				topics: this.state.settings.topics
			})
			.then(result =>
				this.setState({
					settings: result.data,
					isLoading: false
				})
			)
			.catch(function(error) {
				console.log(error);
			});

		/* this.setState(prevState => ({
			isToggleOn: !prevState.isToggleOn
		})); */
	}

	render() {
		var ignoredMeasurements =
			'FAULT_REPORTING,ERROR,LOWBAT,LED_STATUS,UNREACH,STICKY_UNREACH,CONTROL_MODE,COMMUNICATION_REPORTING,PARTY_STOP_MONTH,PARTY_START_MONTH,PARTY_STOP_DAY,PARTY_STOP_TIME,PARTY_STOP_YEAR,WINDOW_OPEN_REPORTING,LOWBAT_REPORTING,PARTY_START_YEAR,PARTY_START_TIME,PARTY_START_DAY,CONFIG_PENDING';

		var rooms = 'labor';
		var types =
			'Bewegungsmelder,Heizungsthermostat,Wandthermostat,Fenster/Tuer-kontakt,ToDo';

		return (
			<div>
				<h1>Connect to MQTT Broker</h1>
				<h3>Broker settings</h3>
				<Grid container>
					<GridItem xs={12} sm={12} md={4}>
						<CustomInput
							labelText="Broker Address"
							id="brokerAddress"
							formControlProps={{
								fullWidth: true
							}}
							inputProps={{
								value: this.state.brokerAddress,
								onChange: this.handleChange
							}}
						/>
					</GridItem>
					<GridItem xs={12} sm={12} md={4}>
						<CustomInput
							labelText="Broker Username"
							id="brokerUsername"
							inputProps={{
								value: this.state.brokerUsername,
								onChange: this.handleChange
							}}
							formControlProps={{
								fullWidth: true
							}}
						/>
					</GridItem>
					<GridItem xs={12} sm={12} md={4}>
						<CustomInput
							labelText="Broker Password"
							id="brokerPassword"
							inputProps={{
								value: this.state.brokerPassword,
								onChange: this.handleChange
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
						{ignoredMeasurements}
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
						th/hm/status/
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
					{/* <GridItem xs={12} sm={12} md={4}>
						<CustomInput
							labelText="Types"
							id="types"
							inputProps={{
								defaultValue: types,
								multiline: true
							}}
							formControlProps={{
								fullWidth: true
							}}
						/>
					</GridItem>
					<GridItem xs={12} sm={12} md={4}>
						<CustomInput
							labelText="Rooms"
							id="rooms"
							inputProps={{
								defaultValue: rooms,
								multiline: true
							}}
							formControlProps={{
								fullWidth: true
							}}
						/>
					</GridItem> */}
				</Grid>

				<Button type="button" color="info" onClick={this.createMqttConnection}>
					Update
				</Button>

				<Button type="button" color="info" onClick={this.createMqttConnection2}>
					Connect
				</Button>
			</div>
		);
	}
}

/* class BrokerSettings extends Component {
	constructor(props) {
		super(props);
		this.state = {
			brokerSettings: [],
			error: null
		};
	}

	componentDidMount() {}

	render() {
		return <div />;
	}
} */
