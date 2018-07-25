import React, { Component } from 'react';
import axios from 'axios';
import Button from 'components/MaterialUI/CustomButtons/Button.jsx';
import Grid from '@material-ui/core/Grid';
import CustomInput from 'components/MaterialUI/CustomInput/CustomInput.jsx';
import GridItem from 'components/MaterialUI/Grid/GridItem.jsx';

export default class SensorTypes extends Component {
	constructor(props) {
		super(props);
		this.state = {
			types: [],
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
			.get('http://localhost:8090/settings/types')
			.then(result =>
				this.setState({
					types: result.data,
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
			.post('http://localhost:8090/settings/types', this.state.newValue, {
				headers: {
					'Content-Type': 'text/plain'
				}
			})
			.then(result =>
				this.setState({
					newValue: '',
					types: result.data,
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
			.patch('http://localhost:8090/settings/types', this.state.newValue, {
				headers: {
					'Content-Type': 'text/plain',
					'Access-Control-Allow-Origin': '*'
				}
			})
			.then(result =>
				this.setState({
					newValue: '',
					types: result.data,
					isLoading: false
				})
			)
			.catch(function(error) {
				console.log(error);
			});
	}

	render() {
		var types = this.state.types.map(type => <li key={type}>{type}</li>);
		if (types.length === 0) {
			types = 'No Types to display';
		}
		if (this.state.error) {
			return <div>Can't connect to database</div>;
		}

		return (
			<div>
				<h1>Types</h1>
				<ul>{types}</ul>
				<Grid container>
					<GridItem xs={12} sm={12} md={4}>
						<CustomInput
							labelText="New type"
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
							Add Type
						</Button>
					</GridItem>
					<GridItem xs={12} sm={12} md={12}>
						<Button type="button" color="danger" onClick={this.removeValue}>
							Remove Type
						</Button>
					</GridItem>
				</Grid>
			</div>
		);
	}
}
