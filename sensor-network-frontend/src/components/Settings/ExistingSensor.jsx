import React, { Component } from 'react';
import axios from 'axios';
import Grid from '@material-ui/core/Grid';
import CustomInput from 'components/MaterialUI/CustomInput/CustomInput.jsx';
import GridItem from 'components/MaterialUI/Grid/GridItem.jsx';
import Button from 'components/MaterialUI/CustomButtons/Button.jsx';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import Icon from '@material-ui/core/Icon';

export default class ExistingSensor extends Component {
	constructor(props) {
		super(props);
		this.state = {
			sensor: this.props.sensor
		};
		this.handleChange = this.handleChange.bind(this);
		this.updateSesnor = this.updateSesnor.bind(this);
		this.deleteSensor = this.deleteSensor.bind(this);
	}

	handleChange(event) {
		this.setState({
			sensor: {
				...this.state.sensor,
				[event.target.id]: event.target.value
			}
		});
		console.log(this.state.sensor);
	}

	updateSesnor() {
		this.setState({ isLoading: true });
		axios
			.patch('http://localhost:8090/sensors', this.state.sensor)
			.then(
				result =>
					this.setState({
						sensor: result.data,
						isLoading: false
					}),
				console.log(this.state.sensor),
				alert('Updated sesnor: ' + this.state.sensor._id)
			)
			.catch(function(error) {
				console.log(error);
			});
	}

	deleteSensor(event) {
		this.setState({ isLoading: true });
		axios
			.delete('http://localhost:8090/sensors/' + event.currentTarget.id)
			.then(
				result =>
					this.setState({
						isLoading: false,
						sensor: {}
					}),
				alert('Sensor delted :' + event.currentTarget.id)
			)
			.catch(function(error) {
				console.log(error);
			});
	}

	render() {
		let optionItemsProducts = this.props.sensorProducts.map(sp => (
			<option value={sp._id}>{sp._id}</option>
		));

		let optionItemsRooms = this.props.sensorRooms.map(room => (
			<option value={room}>{room}</option>
		));

		return (
			<div className="sensor" key={this.props.sensorID}>
				<Grid container key={this.props.sensorID}>
					<GridItem xs={12} sm={12} md={4}>
						<CustomInput
							labelText="Sensor Name"
							id="sensorName"
							formControlProps={{
								fullWidth: true
							}}
							inputProps={{
								multiline: true,
								value: this.state.sensor.sensorName,
								onChange: this.handleChange
							}}
						/>
					</GridItem>
					<GridItem xs={12} sm={12} md={4}>
						<div className="root">
							<FormControl className="formControl">
								<InputLabel htmlFor="room">Sensor Room</InputLabel>
								<Select
									native
									value={this.state.sensor.room}
									onChange={this.handleChange}
									inputProps={{
										name: 'room',
										id: 'room'
									}}
								>
									<option value="" />
									{optionItemsRooms}
								</Select>
							</FormControl>
						</div>
					</GridItem>
					<GridItem xs={12} sm={12} md={3}>
						<div className="root">
							<FormControl className="formControl">
								<InputLabel htmlFor="sensorProductID">
									Sensor Product
								</InputLabel>
								<Select
									native
									value={this.state.sensor.sensorProductID}
									onChange={this.handleChange}
									inputProps={{
										name: 'sensorProductID',
										id: 'sensorProductID'
									}}
								>
									<option value="" />
									{optionItemsProducts}
								</Select>
							</FormControl>
						</div>
					</GridItem>
					<GridItem xs={12} sm={2} md={1}>
						<Button
							type="button"
							justIcon
							round
							color="danger"
							id={this.state.sensor._id}
							name={this.state.sensor._id}
							onClick={this.deleteSensor}
						>
							<Icon>clear</Icon>
						</Button>
					</GridItem>
				</Grid>
				<Button type="button" color="success" round onClick={this.updateSesnor}>
					Update Sensor
				</Button>
				<br />
				<br />
				<br />
			</div>
		);
	}
}
