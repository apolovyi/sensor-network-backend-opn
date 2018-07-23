import React, { Component } from 'react';
import axios from 'axios';
import Button from 'components/CustomButtons/Button.jsx';
import Grid from '@material-ui/core/Grid';
import CustomInput from 'components/CustomInput/CustomInput.jsx';
import GridItem from 'components/Grid/GridItem.jsx';

export default class SensorRooms extends Component {
	constructor(props) {
		super(props);

		this.state = {
			rooms: [],
			isLoading: false,
			error: null,
			newValue: ''
		};
		this.addValue = this.addValue.bind(this);
		this.removeValue = this.removeValue.bind(this);
		this.handleChange = this.handleChange.bind(this);
	}

	handleChange(event) {
		this.setState({ [event.target.id]: event.target.value });
	}

	componentDidMount() {
		this.setState({ isLoading: true });

		axios
			.get('http://localhost:8090/settings/rooms')
			.then(result =>
				this.setState({
					rooms: result.data,
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

	addValue() {
		this.setState({ isLoading: true });
		axios
			.post('http://localhost:8090/settings/rooms', this.state.newValue, {
				headers: {
					'Content-Type': 'text/plain'
				}
			})
			.then(result =>
				this.setState({
					newValue: '',
					rooms: result.data,
					isLoading: false
				})
			)
			.catch(function(error) {
				console.log(error);
			});
	}

	removeValue() {
		this.setState({ isLoading: true });
		axios
			.patch('http://localhost:8090/settings/rooms', this.state.newValue, {
				headers: {
					'Content-Type': 'text/plain',
					'Access-Control-Allow-Origin': '*'
				}
			})
			.then(result =>
				this.setState({
					newValue: '',
					rooms: result.data,
					isLoading: false
				})
			)
			.catch(function(error) {
				console.log(error);
			});
	}

	render() {
		var rooms = this.state.rooms.map(room => <li key={room}>{room}</li>);
		if (rooms.length === 0) {
			rooms = 'No Rooms to display';
		}
		if (this.state.error) {
			return <div>Can't connect to database</div>;
		}

		return (
			<div>
				<h1>Rooms</h1>
				<ul>{rooms}</ul>
				<Grid container>
					<GridItem xs={12} sm={12} md={4}>
						<CustomInput
							labelText="New room"
							id="newValue"
							formControlProps={{
								fullWidth: true
							}}
							inputProps={{
								value: this.state.newValue,
								onChange: this.handleChange
							}}
						/>
					</GridItem>
				</Grid>
				<Grid container>
					<GridItem xs={12} sm={12} md={12}>
						<Button type="button" color="success" onClick={this.addValue}>
							Add Room
						</Button>
					</GridItem>
					<GridItem xs={12} sm={12} md={12}>
						<Button type="button" color="danger" onClick={this.removeValue}>
							Remove Room
						</Button>
					</GridItem>
				</Grid>
			</div>
		);
	}
}
