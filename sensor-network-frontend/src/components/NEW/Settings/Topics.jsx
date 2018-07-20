import React, { Component } from 'react';
import axios from 'axios';

export default class Topics extends Component {
	constructor(props) {
		super(props);

		this.state = {
			topics: [],
			isLoading: false,
			error: null
		};
	}

	componentDidMount() {
		this.setState({ isLoading: true });

		axios
			.get('http://localhost:8090/settings/topics')
			.then(result =>
				this.setState({
					topics: result.data,
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
		var topics = this.state.topics.map((topic, index) => (
			<li key={topic}>{topic}</li>
		));
		if (topics.length === 0) {
			topics = 'No Topics to display';
		}
		if (this.state.error) {
			return <div>Can't connect to database</div>;
		}

		return (
			<div>
				<h1>Subscribed Topics</h1>
				<ul>{topics}</ul>
			</div>
		);
	}
}
