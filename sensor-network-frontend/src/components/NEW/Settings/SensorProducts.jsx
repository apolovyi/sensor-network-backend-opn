import React, { Component } from 'react';
import axios from 'axios';
import Button from 'components/CustomButtons/Button.jsx';
import Grid from '@material-ui/core/Grid';
import CustomInput from 'components/CustomInput/CustomInput.jsx';
import GridItem from 'components/Grid/GridItem.jsx';
import Muted from 'components/Typography/Muted.jsx';

export default class SensorProducts extends Component {
	constructor(props) {
		super(props);

		this.state = {
			sensorProducts: [],
			isLoading: false,
			error: null,
			type: '',
			producer: '',
			semantic: {
				ts: '',
				value: '',
				unit: ''
			}
		};
		this.addSensorProduct = this.addSensorProduct.bind(this);
		this.deleteSensorProduct = this.deleteSensorProduct.bind(this);
		this.handleChange = this.handleChange.bind(this);
		this.handleChangeOnSemantic = this.handleChangeOnSemantic.bind(this);
	}

	componentDidMount() {
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
	}

	handleChange(event) {
		this.setState({ [event.target.id]: event.target.value });
	}

	handleChangeOnSemantic(event) {
		this.setState({
			semantic: {
				...this.state.semantic,
				[event.target.id]: event.target.value
			}
		});
	}

	addSensorProduct() {
		this.setState({ isLoading: true });
		axios
			.post(
				'http://localhost:8090/settings/sensorProducts',
				{
					type: this.state.type,
					producer: this.state.producer,
					semantic: this.state.semantic
				},
				{
					headers: {
						'Content-Type': 'application/json'
					}
				}
			)
			.then(result =>
				this.setState({
					type: '',
					producer: '',
					semantic: {
						ts: '',
						value: '',
						unit: ''
					},
					sensorProducts: result.data,
					isLoading: false
				})
			)
			.catch(function(error) {
				console.log(error);
			});
	}

	deleteSensorProduct(event) {
		this.setState({ isLoading: true });
		axios
			.patch(
				'http://localhost:8090/settings/sensorProducts',
				event.currentTarget.id,
				{
					headers: {
						'Content-Type': 'text/plain'
					}
				}
			)
			.then(result =>
				this.setState({
					sensorProducts: result.data,
					isLoading: false
				})
			)
			.catch(function(error) {
				console.log(error);
			});
	}

	render() {
		function renderSemantic(semantic) {
			return (
				<div>
					<ul>
						<li>{semantic.ts}</li>
						<li>{semantic.value}</li>
						<li>{semantic.unit}</li>
					</ul>
				</div>
			);
		}

		var sensorProducts = this.state.sensorProducts.map(sp => (
			<div key={sp._id}>
				<span>
					<h5>{sp._id}</h5>
					<Muted>Semantic</Muted>
					{renderSemantic(sp.semantic)}
				</span>
				<Button
					id={sp._id}
					type="button"
					color="danger"
					onClick={this.deleteSensorProduct}
				>
					Delete Sensor Product
				</Button>
			</div>
		));
		if (sensorProducts.length === 0) {
			sensorProducts = 'No Sensor Products to display';
		}
		if (this.state.error) {
			return <div>Can't connect to database</div>;
		}

		return (
			<div>
				<h1>Existing Sensor Products</h1>
				<div>{sensorProducts}</div>

				<h3>New Sensor Product:</h3>
				<Grid container>
					<GridItem xs={12} sm={12} md={4}>
						<CustomInput
							labelText="Producer"
							id="producer"
							formControlProps={{
								fullWidth: true
							}}
							inputProps={{
								value: this.state.producer,
								onChange: this.handleChange
							}}
						/>
					</GridItem>
					<GridItem xs={12} sm={12} md={4}>
						<CustomInput
							labelText="Type"
							id="type"
							formControlProps={{
								fullWidth: true
							}}
							inputProps={{
								value: this.state.type,
								onChange: this.handleChange
							}}
						/>
					</GridItem>
					<GridItem xs={12} sm={12} md={12}>
						<h6>Semantic</h6>
					</GridItem>
					<GridItem xs={12} sm={12} md={4}>
						<CustomInput
							labelText="Timestamp key"
							id="ts"
							formControlProps={{
								fullWidth: true
							}}
							inputProps={{
								value: this.state.semantic.ts,
								onChange: this.handleChangeOnSemantic
							}}
						/>
					</GridItem>
					<GridItem xs={12} sm={12} md={4}>
						<CustomInput
							labelText="Value key"
							id="value"
							formControlProps={{
								fullWidth: true
							}}
							inputProps={{
								value: this.state.semantic.value,
								onChange: this.handleChangeOnSemantic
							}}
						/>
					</GridItem>
					<GridItem xs={12} sm={12} md={4}>
						<CustomInput
							labelText="Unit key"
							id="unit"
							formControlProps={{
								fullWidth: true
							}}
							inputProps={{
								value: this.state.semantic.unit,
								onChange: this.handleChangeOnSemantic
							}}
						/>
					</GridItem>
					<GridItem xs={12} sm={12} md={4}>
						<Button
							type="button"
							color="success"
							onClick={this.addSensorProduct}
						>
							Add Sesnor Product
						</Button>
					</GridItem>
				</Grid>
			</div>
		);
	}
}
