<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>RoleplayNeeds — Realistic Body Needs & Thirst System</title>
  <style>
    /* ------------ Base / Reset ------------ */
    :root{
      --bg:#0b0f13;
      --card:#0f1720;
      --muted:#9aa5b1;
      --accent:#00bfff;
      --accent-2:#6ee7b7;
      --glow: 0 10px 40px rgba(0,191,255,0.08);
      --glass: rgba(255,255,255,0.03);
      --mono: "SFMono-Regular", Consolas, "Liberation Mono", Menlo, monospace;
      --radius:14px;
    }
    *{box-sizing:border-box}
    html,body{height:100%}
    body{
      margin:0;
      font-family:Inter, ui-sans-serif, system-ui, -apple-system, "Segoe UI", Roboto, "Helvetica Neue", Arial;
      background:
        radial-gradient(1200px 600px at 10% 10%, rgba(0,191,255,0.03), transparent 8%),
        linear-gradient(180deg, #071017 0%, #05060a 100%);
      color:#e6eef6;
      -webkit-font-smoothing:antialiased;
      -moz-osx-font-smoothing:grayscale;
      line-height:1.45;
      padding:40px;
    }

    /* ------------ Layout ------------ */
    .wrap{max-width:1100px;margin:0 auto}
    header.hero{
      display:flex;
      gap:24px;
      align-items:center;
      margin-bottom:28px;
    }
    .logo{
      min-width:110px;
      min-height:110px;
      display:flex;
      align-items:center;
      justify-content:center;
      border-radius:18px;
      background:linear-gradient(135deg,var(--accent),#6a5cff);
      box-shadow: 0 6px 30px rgba(0,191,255,0.15);
      font-weight:800;
      font-size:28px;
      color:#021017;
      letter-spacing:0.6px;
    }
    .title{
      flex:1;
    }
    .title h1{
      margin:0;
      font-size:28px;
      letter-spacing:-0.4px;
      display:flex;
      gap:12px;
      align-items:center;
    }
    .subtitle{
      margin-top:8px;
      color:var(--muted);
      font-size:14px;
    }

    /* ------------ Cards ------------ */
    .grid{
      display:grid;
      grid-template-columns: 1fr 380px;
      gap:22px;
      align-items:start;
    }
    .panel{
      background: linear-gradient(180deg, rgba(255,255,255,0.02), rgba(255,255,255,0.01));
      border-radius:var(--radius);
      padding:18px;
      box-shadow:var(--glow);
      border:1px solid rgba(255,255,255,0.03);
    }
    .big-panel{grid-column:1 / span 1}
    .side-panel{position:sticky; top:30px}

    .section-title{
      display:flex;
      align-items:center;
      gap:10px;
      margin-bottom:12px;
    }
    .badge{
      background:linear-gradient(90deg,var(--accent),var(--accent-2));
      color:#021017;
      padding:6px 10px;
      border-radius:999px;
      font-weight:700;
      font-size:13px;
      box-shadow:0 6px 18px rgba(0,191,255,0.08);
    }
    h2{margin:0;color:#dff6ff}
    p.lead{color:var(--muted);margin:12px 0 18px}

    /* ------------ Features list ------------ */
    .features{
      display:grid;
      grid-template-columns:repeat(2,1fr);
      gap:12px;
      margin-top:12px;
    }
    .feature{
      padding:12px;
      background:var(--glass);
      border-radius:12px;
      border:1px solid rgba(255,255,255,0.02);
    }
    .feature h3{margin:0;font-size:15px}
    .feature p{margin:6px 0 0;color:var(--muted);font-size:13px}

    /* ------------ Code block ------------ */
    pre.code{
      background:linear-gradient(180deg, rgba(10,14,18,0.6), rgba(6,8,10,0.6));
      border-radius:12px;
      padding:16px;
      overflow:auto;
      font-family:var(--mono);
      font-size:13px;
      border:1px solid rgba(255,255,255,0.035);
      color:#bcdff6;
    }
    code.kv{color:#7ef3ff}
    code.key{color:#9be78f}
    .label{display:inline-block;background: rgba(255,255,255,0.02); padding:6px 10px;border-radius:999px;font-size:12px;color:var(--muted);margin-bottom:8px}

    /* ------------ Table ------------ */
    .table{
      width:100%;
      border-collapse:collapse;
      margin-top:14px;
      font-size:14px;
      overflow:hidden;
      border-radius:12px;
      box-shadow:0 8px 30px rgba(0,0,0,0.6);
    }
    .table th, .table td{
      padding:12px 14px;
      border-bottom:1px solid rgba(255,255,255,0.03);
      text-align:left;
    }
    .table thead th{
      background:linear-gradient(90deg, rgba(255,255,255,0.02), rgba(255,255,255,0.01));
      color:#dffaff;
      font-weight:700;
      border-bottom:1px solid rgba(255,255,255,0.04);
    }
    .table tbody tr:hover td{background:rgba(255,255,255,0.01)}

    /* ------------ Footer / Meta ------------ */
    .meta{display:flex;gap:12px;flex-wrap:wrap;margin-top:14px;color:var(--muted);font-size:13px}
    .pill{background:rgba(255,255,255,0.02); padding:8px 12px;border-radius:999px;border:1px solid rgba(255,255,255,0.02)}
    .cta{
      display:inline-flex;
      gap:8px;
      align-items:center;
      padding:12px 16px;
      background:linear-gradient(90deg,var(--accent),#7c6bff);
      color:#021017;
      font-weight:700;
      border-radius:12px;
      text-decoration:none;
      box-shadow:0 8px 30px rgba(0,191,255,0.12);
    }

    /* small screens */
    @media (max-width:980px){
      .grid{grid-template-columns:1fr; padding-bottom:40px}
      header.hero{flex-direction:row;gap:16px}
    }
  </style>
</head>
<body>
  <div class="wrap">

    <!-- HERO -->
    <header class="hero">
      <div class="logo">RN</div>
      <div class="title">
        <h1>
          RoleplayNeeds — Realistic Body Needs &amp; Thirst System
        </h1>
        <div class="subtitle">🧍‍♂️ The most immersive and hilarious realism plugin for Minecraft roleplay servers</div>
        <div class="meta" style="margin-top:12px">
          <div class="pill">✔ Spigot &amp; Paper</div>
          <div class="pill">✔ PlaceholderAPI Ready</div>
          <div class="pill">✔ Tested: 1.16 → 1.21+</div>
        </div>
      </div>
      <div style="display:flex;flex-direction:column;gap:10px;align-items:flex-end">
        <a class="cta" href="#config">View config</a>
      </div>
    </header>

    <div class="grid">

      <!-- LEFT: BIG PANEL (Main) -->
      <main class="panel big-panel">

        <div class="section-title">
          <span class="badge">About</span>
          <h2>About the Plugin</h2>
        </div>
        <p class="lead">Tired of the same old hunger bar? <strong>RoleplayNeeds</strong> takes your server to the next level of realism — introducing <strong>urination, pooping, and thirst</strong> systems that keep your players alive, laughing, and truly roleplaying. Perfect for survival roleplay, city servers, and hardcore sims.</p>

        <div class="section-title" style="margin-top:8px">
          <span class="badge">Features</span>
          <h2>Core Features</h2>
        </div>

        <div class="features" aria-hidden="false">
          <div class="feature">
            <h3>🚽 Urination</h3>
            <p>Need rises over time. Use <code>/urine</code> to relieve (cooldown). Custom sounds, particles &amp; messages via config. Ignore it and receive slowness → death.</p>
          </div>

          <div class="feature">
            <h3>💩 Pooping</h3>
            <p>Independent "poop" need. Custom sound, block spawn (e.g. BROWN_CONCRETE) and messages. Put realism and humor into every session.</p>
          </div>

          <div class="feature">
            <h3>💧 Thirst (NEW)</h3>
            <p>Players must drink within <strong>2 Minecraft days (~40 min real)</strong>. Bottles = safe; cauldrons/open water = risky (nausea, hunger, diarrhea). Dizziness then death if neglected.</p>
          </div>

          <div class="feature">
            <h3>⚙ Customizable</h3>
            <p>All values, durations, messages and effects are editable in <code>config.yml</code>. Fully supported placeholders for scoreboards and GUIs.</p>
          </div>
        </div>

        <!-- placeholders -->
        <div style="margin-top:20px">
          <div class="label">Placeholders</div>
          <pre class="code" style="margin-top:8px">
%rpneeds_urine%   → Current urine level (%)
%rpneeds_poop%    → Current poop level (%)
%rpneeds_thirst%  → Current thirst level (%)
          </pre>
        </div>

        <!-- THIRST detail -->
        <div style="margin-top:20px">
          <div class="section-title">
            <span class="badge">Thirst Details</span>
            <h2>Thirst System — Risk & Reward</h2>
          </div>

          <p class="lead" style="margin-top:6px">Thirst adds a realistic hydration layer:</p>
          <table class="table" role="table" aria-label="Thirst behavior">
            <thead>
              <tr><th>Action</th><th>Result</th></tr>
            </thead>
            <tbody>
              <tr><td>Drink Water Bottle</td><td>Safe — thirst reset</td></tr>
              <tr><td>Drink from Cauldron / Open Water</td><td>Unsafe — nausea (1m) • diarrhea effects • hunger increase</td></tr>
              <tr><td>No water for 2 MC days (~40m)</td><td>5 minutes dizziness → death from dehydration</td></tr>
            </tbody>
          </table>
        </div>

        <!-- CONFIG -->
        <div id="config" style="margin-top:20px">
          <div class="section-title">
            <span class="badge">Config</span>
            <h2>Highly Customizable `config.yml`</h2>
          </div>

          <p class="lead">Drop into your plugin folder and edit values to tune the experience for your server.</p>

          <pre class="code" aria-hidden="false"><code># ===========================
# RoleplayNeeds Configuration
# ===========================
prefix: "&amp;8[&amp;bNeeds&amp;8]&amp;r "

max-level: 100
increase-interval: 60   # seconds
increase-amount: 1

cooldowns:
  urine: 30
  poop: 30

effects:
  slowness-level: 1
  slowness-duration: 60

# ===========================
# Urine Settings
# ===========================
urine:
  sound: ENTITY_PLAYER_SPLASH
  particle: DRIP_WATER
  message-success: "&amp;eYou relieved yourself!"
  message-cooldown: "&amp;cYou can only urinate every %time% minutes!"

# ===========================
# Poop Settings
# ===========================
poop:
  sound: ENTITY_SLIME_SQUISH
  block: BROWN_CONCRETE
  message-success: "&amp;6You pooped!"
  message-cooldown: "&amp;cYou can only poop every %time% minutes!"

# ===========================
# Thirst Settings
# ===========================
thirst:
  max-days: 2
  dizzy-duration: 300
  death-message: "&amp;cYou died of dehydration!"
  safe-drink-sources:
    - WATER_BOTTLE
  unsafe-sources:
    - CAULDRON
    - WATER
  unsafe-effects:
    nausea-duration: 60
    hunger-duration: 120
    diarrhea-message: "&amp;7You drank dirty water and got sick!"

death-message: "&amp;cYou died because you ignored your needs!"
</code></pre>
        </div>

        <!-- CONSEQUENCES -->
        <div style="margin-top:20px">
          <div class="section-title">
            <span class="badge">Consequences</span>
            <h2>What happens if you ignore needs?</h2>
          </div>

          <table class="table" role="table">
            <thead>
              <tr><th>Action Ignored</th><th>Effect</th></tr>
            </thead>
            <tbody>
              <tr><td>Urine / Poop</td><td>Slowness + eventual death</td></tr>
              <tr><td>Thirst</td><td>Dizziness → Weakness → Dehydration death</td></tr>
              <tr><td>Unsafe Water</td><td>Nausea + Hunger debuff (possible diarrhea)</td></tr>
            </tbody>
          </table>
        </div>

        <!-- PLANNED -->
        <div style="margin-top:20px">
          <div class="section-title">
            <span class="badge">Roadmap</span>
            <h2>Planned Updates</h2>
          </div>

          <table class="table" role="table">
            <tbody>
              <tr><td>Sleep &amp; Hygiene system</td></tr>
              <tr><td>Expanded hunger integration</td></tr>
              <tr><td>Water purification items</td></tr>
              <tr><td>Economy support (paid toilets &amp; clean water)</td></tr>
            </tbody>
          </table>
        </div>

      </main>

      <!-- RIGHT: SIDE PANEL -->
      <aside class="panel side-panel">
        <div style="display:flex;flex-direction:column;gap:10px">
          <div style="display:flex;justify-content:space-between;align-items:center">
            <div>
              <div style="font-size:13px;color:var(--muted)">Plugin</div>
              <div style="font-weight:700;font-size:18px">RoleplayNeeds</div>
            </div>
            <div style="text-align:right">
              <div style="font-size:12px;color:var(--muted)">Version</div>
              <div style="font-weight:700">v1.0.0</div>
            </div>
          </div>

          <div style="margin-top:6px">
            <div class="label">Quick Actions</div>
            <div style="display:flex;gap:8px;margin-top:8px">
              <a class="cta" href="#" style="padding:10px 12px;font-size:13px">Download</a>
              <a class="cta" href="#" style="padding:10px 12px;font-size:13px;background:linear-gradient(90deg,#7c6bff,#ff7ab6);">Source</a>
            </div>
          </div>

          <div style="margin-top:14px">
            <div class="label">Placeholders</div>
            <table class="table" style="margin-top:8px">
              <thead><tr><th>Placeholder</th><th>What it shows</th></tr></thead>
              <tbody>
                <tr><td>%rpneeds_urine%</td><td>Current urine %</td></tr>
                <tr><td>%rpneeds_poop%</td><td>Current poop %</td></tr>
                <tr><td>%rpneeds_thirst%</td><td>Current thirst %</td></tr>
              </tbody>
            </table>
          </div>

          <div style="margin-top:14px">
            <div class="label">Compatibility</div>
            <table class="table" style="margin-top:8px">
              <tbody>
                <tr><td>Spigot / Paper</td></tr>
                <tr><td>1.16 → 1.21+</td></tr>
                <tr><td>PlaceholderAPI</td></tr>
                <tr><td>Lightweight &amp; roleplay-focused</td></tr>
              </tbody>
            </table>
          </div>

          <div style="margin-top:14px">
            <div class="label">Credits</div>
            <div style="color:var(--muted);font-size:13px">Developed by <strong>Foad</strong><br>Built with ❤️ for Roleplay &amp; Survival servers.</div>
          </div>

        </div>
      </aside>

    </div> <!-- grid -->

    <footer style="margin-top:30px;text-align:center;color:var(--muted)">
      <div>If your players ever said “this server needs more realism” — RoleplayNeeds delivers it with style, chaos, and laughter.</div>
      <div style="margin-top:8px;font-size:13px">Hold it in too long, and you’ll regret it... 💀</div>
    </footer>

  </div>
</body>
</html>
