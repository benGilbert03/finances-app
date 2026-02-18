import React, { useState, useEffect, useContext } from "react";
import { UserContext } from "../UserContext";
import axios from "axios";

function Home() {
	const { userId } = useContext(UserContext);
	const [budgets, setBudgets] = useState([]);
	const [selectedBudget, setSelectedBudget] = useState(null);
	const [isCollapsed, setIsCollapsed] = useState(false);

	const [showModal, setShowModal] = useState(false);
	const [newBudget, setNewBudget] = useState({
		name: "",
		amount: 0,
		frequency: "MONTHLY"
	});

	useEffect(() => {
		if (userId > 0) {
			fetchBudgets();
		}
	}, [userId]);

	const fetchBudgets = () => {
		axios.get(`http://localhost:8080/budget/account/${userId}`)
			.then(res => setBudgets(res.data))
			.catch(err => console.error("Error fetching budgets: ", err));
	};

	const refreshSelectedBudget = () => {
		axios.get(`http://localhost:8080/budget/budget/${selectedBudget.id}`)
			.then(res => setSelectedBudget(res.data))
			.catch(err => console.error(err));
		fetchBudgets();
	};

	const handleCreateBudget = async (e) => {
		e.preventDefault();
		try {
			const createRes = await axios.post(`http://localhost:8080/budget/${newBudget.frequency}`);
			const newId = createRes.data;

			await axios.put(`http://localhost:8080/budget/account`, null, {
				params: { budgetId: newId, accountId: userId }
			});

			await axios.put(`http://localhost:8080/budget/name/${newId}/${encodeURIComponent(newBudget.name)}`);
			await axios.put(`http://localhost:8080/budget/budget/${newId}/General/${newBudget.amount}`);

			fetchBudgets();
			setShowModal(false);
			setNewBudget({ name: "", amount: 0, frequency: "MONTHLY" });
		} catch (err) {
			console.error("Sequence failed: ", err);
			alert("Error creating budget.");
		}
	};

	const handleUpdateCategoryLimit = async (category, amount) => {
		if (!category || !amount) return;
		try {
			// Encode category in case of spaces/special chars
			await axios.put(`http://localhost:8080/budget/budget/${selectedBudget.id}/${encodeURIComponent(category)}/${amount}`);
			refreshSelectedBudget();
			// Clear inputs manually if needed
			document.getElementById("newCatName").value = "";
			document.getElementById("newCatLimit").value = "";
		} catch (err) {
			console.error("Error updating category:", err);
		}
	};

	const handleUpdateSpend = async (category, amount) => {
		try {
			await axios.put(`http://localhost:8080/budget/spend/${selectedBudget.id}/${encodeURIComponent(category)}/${amount}`);
			refreshSelectedBudget();
		} catch (err) {
			console.error("Error updating spend:", err);
		}
	};

	const handleRenameCategory = async (oldName) => {
		const newName = prompt("Enter new name for " + oldName);
		if (!newName || newName === oldName) return;

		try {
			await axios.put(`http://localhost:8080/budget/rename-category/${selectedBudget.id}/${encodeURIComponent(oldName)}/${encodeURIComponent(newName)}`);
			refreshSelectedBudget();
		} catch (err) {
			console.error("Rename failed:", err);
		}
	};

	return (
		<div style={{ display: "flex", height: "100vh", fontFamily: 'Segoe UI', backgroundColor: "#f4f7f6" }}>
			{/* SIDEBAR */}
			<div style={{
				width: isCollapsed ? "60px" : "260px",
				transition: "0.3s",
				background: "#2c3e50",
				color: "white",
				padding: "15px",
				display: "flex",
				flexDirection: "column"
			}}>
				<button
					onClick={() => setIsCollapsed(!isCollapsed)}
					style={{ background: "none", border: "1px solid white", color: "white", cursor: "pointer", marginBottom: "20px" }}
				>
					{isCollapsed ? "→" : "Collapse Menu"}
				</button>

				{!isCollapsed && (
					<>
						<h3>My Budgets</h3>
						<ul style={{ listStyle: "none", padding: 0, flex: 1 }}>
							{budgets.map(b => (
								<li
									key={b.id}
									onClick={() => setSelectedBudget(b)}
									style={{
										padding: "12px",
										margin: "5px 0",
										borderRadius: "5px",
										cursor: "pointer",
										backgroundColor: selectedBudget?.id === b.id ? "#34495e" : "transparent"
									}}
								>
									{b.name}
								</li>
							))}
						</ul>
						<button
							onClick={() => setShowModal(true)}
							style={{ padding: "12px", backgroundColor: "#27ae60", color: "white", border: "none", borderRadius: "5px", cursor: "pointer" }}
						>
							+ Add Budget
						</button>
					</>
				)}
			</div>

			{/* MAIN CONTENT */}
			<div style={{ flex: 1, padding: "40px", overflowY: "auto" }}>
				{selectedBudget ? (
					<div style={{ background: "white", padding: "30px", borderRadius: "8px", boxShadow: "0 2px 10px rgba(0,0,0,0.1)" }}>
						<h2 style={{ marginTop: 0 }}>{selectedBudget.name} <span style={{ fontSize: "0.5em", color: "#7f8c8d" }}>({selectedBudget.frequency})</span></h2>

						<div style={{ display: "flex", gap: "20px", marginBottom: "30px" }}>
							<div style={statCard}>
								<small>Total Budget</small><br />
								<strong>${selectedBudget.totalBudget}</strong>
							</div>
							<div style={statCard}>
								<small>Total Spent</small><br />
								<strong style={{ color: "#e74c3c" }}>${selectedBudget.totalSpend}</strong>
							</div>
						</div>

						<h3>Categories</h3>
						<table style={{ width: "100%", borderCollapse: "collapse" }}>
							<thead>
								<tr style={{ borderBottom: "2px solid #eee", textAlign: "left" }}>
									<th style={{ padding: "10px" }}>Category</th>
									<th style={{ padding: "10px" }}>Limit</th>
									<th style={{ padding: "10px" }}>Spent</th>
									<th style={{ padding: "10px" }}>Actions</th>
								</tr>
							</thead>
							<tbody>
								{Object.keys(selectedBudget.categoryBudget || {}).map(cat => (
									<tr key={cat} style={{ borderBottom: "1px solid #eee" }}>
										<td style={{ padding: "10px" }}>
											{cat}
											<button
												onClick={() => handleRenameCategory(cat)}
												style={{ marginLeft: '10px', fontSize: '10px', padding: '2px 5px' }}
											>
												✎
											</button>
										</td>
										<td style={{ padding: "10px" }}>${selectedBudget.categoryBudget[cat]}</td>
										<td style={{ padding: "10px" }}>${selectedBudget.categorySpend[cat] || 0}</td>
										<td style={{ padding: "10px" }}>
											<button
												style={actionButtonStyle}
												onClick={() => {
													const amt = prompt("Enter total spending amount for " + cat);
													if (amt) handleUpdateSpend(cat, amt);
												}}>Log Spend</button>
										</td>
									</tr>
								))}
							</tbody>
						</table>

						<div style={{ marginTop: "30px", padding: "20px", background: "#f9f9f9", borderRadius: "8px" }}>
							<h4>Add/Adjust Category</h4>
							<input id="newCatName" placeholder="Category Name" style={smallInputStyle} />
							<input id="newCatLimit" type="number" placeholder="Limit" style={smallInputStyle} />
							<button
								style={{ ...actionButtonStyle, backgroundColor: "#27ae60", color: "white" }}
								onClick={() => {
									const name = document.getElementById("newCatName").value;
									const limit = document.getElementById("newCatLimit").value;
									handleUpdateCategoryLimit(name, limit);
								}}>Update Category</button>
						</div>
					</div>
				) : (
					<div style={{ textAlign: "center", marginTop: "100px", color: "#95a5a6" }}>
						<h2>Select a budget to manage categories</h2>
					</div>
				)}
			</div>

			{/* MODAL */}
			{showModal && (
				<div style={modalOverlayStyle}>
					<div style={{ background: "white", padding: "30px", borderRadius: "10px", width: "350px" }}>
						<h2 style={{ marginTop: 0 }}>New Budget</h2>
						<form onSubmit={handleCreateBudget}>
							<label>Budget Name</label>
							<input
								type="text" required style={inputStyle}
								onChange={(e) => setNewBudget({ ...newBudget, name: e.target.value })}
							/>
							<label>Amount Limit</label>
							<input
								type="number" required style={inputStyle}
								onChange={(e) => setNewBudget({ ...newBudget, amount: e.target.value })}
							/>
							<label>Frequency</label>
							<select
								style={inputStyle}
								onChange={(e) => setNewBudget({ ...newBudget, frequency: e.target.value })}
							>
								<option value="DAILY">Daily</option>
								<option value="WEEKLY">Weekly</option>
								<option value="MONTHLY">Monthly</option>
								<option value="ANNUALLY">Annually</option>
							</select>

							<div style={{ display: "flex", gap: "10px", marginTop: "10px" }}>
								<button type="submit" style={{ flex: 2, padding: "10px", background: "#27ae60", color: "white", border: "none", borderRadius: "5px", cursor: "pointer" }}>Create</button>
								<button type="button" onClick={() => setShowModal(false)} style={{ flex: 1, padding: "10px", cursor: "pointer" }}>Cancel</button>
							</div>
						</form>
					</div>
				</div>
			)}
		</div>
	);
}

// --- STYLES ---

const statCard = {
	flex: 1,
	padding: "20px",
	borderRadius: "8px",
	background: "#ecf0f1",
	textAlign: "center",
	fontSize: "1.2rem"
};

const inputStyle = {
	width: "100%",
	padding: "10px",
	margin: "10px 0 20px 0",
	borderRadius: "5px",
	border: "1px solid #ddd",
	boxSizing: "border-box"
};

const smallInputStyle = {
	padding: "8px",
	marginRight: "10px",
	borderRadius: "4px",
	border: "1px solid #ccc"
};

const actionButtonStyle = {
	padding: "6px 12px",
	cursor: "pointer",
	borderRadius: "4px",
	border: "1px solid #ddd",
	background: "white"
};

const modalOverlayStyle = {
	position: "fixed", top: 0, left: 0, width: "100%", height: "100%",
	backgroundColor: "rgba(0,0,0,0.7)", display: "flex", justifyContent: "center", alignItems: "center", zIndex: 1000
};

export default Home;