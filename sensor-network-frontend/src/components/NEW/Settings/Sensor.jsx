import React, { Component } from 'react';
import Measurements from './Measurements';
import axios from 'axios';

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
		margin: 27,
		minWidth: 180
	},
	selectEmpty: {
		marginTop: theme.spacing.unit * 2
	}
});

class Sensor extends Component {
	constructor(props) {
		super(props);
		this.state = {
			sensorProducts: [],
			sensorRooms: [],
			sensorProduct: '',
			sensorName: this.props.sensorID,
			sensorRoom: ''
		};
		this.handleChange = this.handleChange.bind(this);
		this.addSensor = this.addSensor.bind(this);
	}

	handleChange(event) {
		this.setState({ [event.target.id]: event.target.value });
	}

	addSensor() {
		this.setState({ isLoading: true });
		axios
			.post('http://localhost:8090/settings/sensors', {
				name: this.state.sensorName,
				room: this.state.sensorRoom,
				spID: this.state.sensorProduct,
				temporarySensor: this.props.tempSensor
			})
			.then(result =>
				this.setState({
					temporarySensors: result.data.temporarySensors,
					isLoading: false
				})
			)
			.catch(function(error) {
				console.log(error);
			});
	}

	render() {
		const { classes } = this.props;

		let optionItemsProducts = this.props.sensorProducts.map(sp => (
			<option value={sp._id}>{sp._id}</option>
		));

		let optionItemsRooms = this.props.sensorRooms.map(room => (
			<option value={room}>{room}</option>
		));

		return (
			<div className="sensor" key={this.props.sensorID}>
				<h4>ID: {this.props.sensorID}</h4>
				<Grid container>
					<GridItem xs={12} sm={12} md={5}>
						<CustomInput
							labelText="Sensor Name"
							id="sensorName"
							formControlProps={{
								fullWidth: true
							}}
							inputProps={{
								multiline: true,
								value: this.state.sensorName,
								onChange: this.handleChange
							}}
						/>
					</GridItem>
					<GridItem xs={12} sm={12} md={4}>
						<div className={classes.root}>
							<FormControl className={classes.formControl}>
								<InputLabel htmlFor="sensorRoom">Sensor Room</InputLabel>
								<Select
									native
									value={this.state.sensorRoom}
									onChange={this.handleChange}
									inputProps={{
										name: 'sensorRoom',
										id: 'sensorRoom'
									}}
								>
									<option value="" />
									{optionItemsRooms}
								</Select>
							</FormControl>
						</div>
					</GridItem>
					<GridItem xs={12} sm={12} md={3}>
						<div className={classes.root}>
							<FormControl className={classes.formControl}>
								<InputLabel htmlFor="sensorProduct">Sensor Product</InputLabel>
								<Select
									native
									value={this.state.sensorProduct}
									onChange={this.handleChange}
									inputProps={{
										name: 'sensorProduct',
										id: 'sensorProduct'
									}}
								>
									<option value="" />
									{optionItemsProducts}
								</Select>
							</FormControl>
						</div>
					</GridItem>
				</Grid>
				<Measurements measurements={this.props.measurements} />
				<Button type="button" color="success" round onClick={this.addSensor}>
					Add Sensor
				</Button>
				<br />
				<br />
				<br />
				<br />
			</div>
		);

		//<ul>{this.props.topics.map(topic => <li>{topic.name}</li>)}</ul>;
	}
}

export default withStyles(styles)(Sensor);
