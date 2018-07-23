import React, { Component } from 'react';
import axios from 'axios';
import SavedSensor from './SavedSensor';
import Button from 'components/CustomButtons/Button.jsx';
import Grid from '@material-ui/core/Grid';
import CustomInput from 'components/CustomInput/CustomInput.jsx';
import GridItem from 'components/Grid/GridItem.jsx';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';

export default class SavedSensors extends Component {
	constructor(props) {
		super(props);

		this.state = {
			savedSensors: [],
			isLoading: false,
			error: null,
			rooms: [],
			sensorRoom: ''
			//sensorRooms: [],
			//sensorRooms: []
		};
		this.filter = this.filter.bind(this);
		this.handleChange = this.handleChange.bind(this);
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

	handleChange(event) {
		this.setState({ [event.currentTarget.id]: event.currentTarget.value });
	}

	filter() {
		//this.setState({ isLoading: true });
		var room = this.state.sensorRoom;

		axios
			.get('http://localhost:8090/sensors?room=' + room)
			.then(result =>
				this.setState({
					savedSensors: result.data,
					isLoading: false
				})
			)
			.catch(function(error) {
				console.log(error);
			});
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
		/* var savedSensor = this.state.savedSensors.map(sensor => (
			<SavedSensor
				key={sensor._id}
				sensorID={sensor._id}
				measurements={sensor.measurements}
				sensorName={sensor.sensorName}
				room={sensor.room}
				tempSensor={sensor}
			/>
		));
		if (savedSensor.length === 0) {
			return 'Sensor database is empty';
		} */
		if (this.state.error) {
			return <div>Can't connect to database</div>;
		}

		let optionItemsProducts = this.state.rooms.map(room => (
			<option value={room}>{room}</option>
		));

		const ListRenderer = ({ list, room }) => {
			const roomFilter = room ? x => x.room === room : x => x;
			return list.length !== 0 ? (
				<div>
					{list
						.filter(roomFilter)
						.map(sensor => (
							<SavedSensor
								key={sensor._id}
								sensorID={sensor._id}
								measurements={sensor.measurements}
								sensorName={sensor.sensorName}
								room={sensor.room}
								tempSensor={sensor}
							/>
						))}
				</div>
			) : (
				'Sensor database is empty'
			);
		};

		return (
			<div>
				<h1>Existing Data</h1>
				<Grid container>
					<GridItem xs={12} sm={12} md={4}>
						<div className="root">
							<FormControl className="formControl">
								<InputLabel htmlFor="sensorRoom">Room</InputLabel>
								<Select
									native
									value={this.state.sensorRoom}
									onChange={this.handleChange}
									inputProps={{
										id: 'sensorRoom'
									}}
								>
									<option value="">All</option>
									{optionItemsProducts}
								</Select>
							</FormControl>
						</div>
					</GridItem>
				</Grid>
				<Grid container>
					<GridItem xs={12} sm={12} md={12}>
						<ListRenderer
							list={this.state.savedSensors}
							room={this.state.sensorRoom}
						/>
					</GridItem>
				</Grid>
			</div>
		);
	}
}
